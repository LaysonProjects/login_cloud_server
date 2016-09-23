package com.baizhi.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.alibaba.fastjson.JSON;
import com.baizhi.dao.IUserDAO;
import com.baizhi.entity.BasicUser;
import com.baizhi.framework.jedis.JedisUtils;
import com.baizhi.service.IUserService;
import com.baizhi.util.AESUtil;
import com.baizhi.vo.LogoutReturnMessage;
import com.baizhi.vo.Ticket;
import com.baizhi.vo.TicketReturnMessage;
import com.baizhi.vo.Token;
import com.baizhi.vo.TokenReturnMessage;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDao;

	public IUserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDAO userDao) {
		this.userDao = userDao;
	}

	public String login(String username, String password, String appName) {

		BasicUser login = userDao.login(username, password);

		// 认证成功
		if (login != null) {
			TokenReturnMessage trm = new TokenReturnMessage("100", "success");

			Set<String> appnames = new HashSet<String>();
			appnames.add(appName);
			Token token = new Token(username, password, appnames);
			String tokenJson = JSON.toJSONString(token);

			// 将token放入redis
			putRedis(username, tokenJson);

			// 加密
			String tokenMessage = null;
			try {

				tokenMessage = AESUtil.aesEncrypt(tokenJson, username);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			trm.setTokenMessage(tokenMessage);

			return JSON.toJSONString(trm);

		} else {

			// 认证失败
			TokenReturnMessage trm = new TokenReturnMessage("101", "error", "");
			return JSON.toJSONString(trm);
		}

	}

	public String getTicket(String username, String tokenMessage, String appname) {

		try {

			String tokenJson = AESUtil.aesDecrypt(tokenMessage, username);
			// 令牌正确，拿到票据
			if (tokenJson.equals(getToken(username))) {
				TicketReturnMessage trm = new TicketReturnMessage();
				trm.setStatusCode("201");
				trm.setMessage("success");
				Ticket t = new Ticket(username, new Date(), appname);
				trm.setTicket(t);

				// 存放票据
				putTicket(username, JSON.toJSONString(t), appname);

				// 更改 token
				String decryptTokenMessage = AESUtil.aesDecrypt(tokenMessage,
						username);
				Token token = JSON
						.parseObject(decryptTokenMessage, Token.class);

				Set<String> appNames = token.getAppname();
				appNames.add(appname);

				// 覆盖redis中的token
				coverToken(username, JSON.toJSONString(token));

				return JSON.toJSONString(trm);

			} else {
				// 失败
				TicketReturnMessage trm = new TicketReturnMessage();
				trm.setStatusCode("200");
				trm.setMessage("error");

				return JSON.toJSONString(trm);
			}

		} catch (Exception e) {
			TicketReturnMessage trm = new TicketReturnMessage();
			// TODO Auto-generated catch block

			trm.setMessage("internet error");
			trm.setStatusCode("210");
			e.printStackTrace();
			return JSON.toJSONString(trm);
		}

	}

	public String checkToken(String username, String tokenMessage,
			String appname) {
		String token = getToken(username);
		try {
			// 加密对比
			String check = AESUtil.aesEncrypt(token, username);
			// 检验
			if (check.equals(tokenMessage)) {
				Token newToken = JSON.parseObject(token, Token.class);
				Set<String> appNames = newToken.getAppname();
				appNames.add(appname);
				String newTokenJson = JSON.toJSONString(newToken);
				// 更改token
				coverToken(username, newTokenJson);

				// token加密返回给用户
				String newTokenMessage = AESUtil.aesEncrypt(newTokenJson,
						username);
				TokenReturnMessage trm = new TokenReturnMessage("301",
						"success", newTokenMessage);
				return JSON.toJSONString(trm);
			} else {
				TokenReturnMessage trm = new TokenReturnMessage("300", "error");
				return JSON.toJSONString(trm);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TokenReturnMessage trm = new TokenReturnMessage("310", "exception");
			return JSON.toJSONString(trm);
		}

	}

	public String logout(String username, String appname) {
		String tokenJson = getToken(username);
		Token token = JSON.parseObject(tokenJson, Token.class);
		Set<String> appNames = token.getAppname();
		boolean flag = false;
		// 清空ticket
		for (String string : appNames) {
			String key = username + "-" + string;
			delTicket(key);
		}
		// 检测是否完全清除
		for (String string : appNames) {
			String key = username + "-" + string;
			flag = checkLogout(key);
			System.out.println("dd"+flag);
			if (flag==true) {
				System.out.println(flag);
				LogoutReturnMessage lrm = new LogoutReturnMessage("400",
						"error");
				return JSON.toJSONString(lrm);
			}

		}

		// 清除token
		delToken(username);

		LogoutReturnMessage lrm = new LogoutReturnMessage("401", "success");
		return JSON.toJSONString(lrm);

	}

	private static void putRedis(String username, String tokenJson) {
		Jedis jedis = JedisUtils.getJedis();
		jedis.select(2);
		jedis.set(username, tokenJson);
		jedis.expire("username", (int) getExSecond());
		JedisUtils.close(jedis);
	}

	private static void delTicket(String key) {
		Jedis jedis = JedisUtils.getJedis();
		jedis.select(3);
		jedis.del(key);
		JedisUtils.close(jedis);
	}

	private static Long delToken(String key) {
		Jedis jedis = JedisUtils.getJedis();
		jedis.select(2);
		Long flag = jedis.del(key);
		JedisUtils.close(jedis);
		return flag;
	}

	private static boolean checkLogout(String key) {
		Jedis jedis = JedisUtils.getJedis();
		jedis.select(3);
		Boolean exists = jedis.exists(key);
		JedisUtils.close(jedis);
		return exists;
	}

	private static void coverToken(String username, String newTokenJson) {
		Jedis jedis = JedisUtils.getJedis();
		jedis.select(2);
		jedis.del(username);
		jedis.set(username, newTokenJson);
		jedis.expire("username", (int) getExSecond());
		JedisUtils.close(jedis);
	}

	private static void putTicket(String username, String ticketJson,
			String appname) {
		String key = username + "-" + appname;
		Jedis jedis = JedisUtils.getJedis();
		jedis.select(3);
		jedis.set(key, ticketJson);
		jedis.expire(key, (int) getExSecond());
		JedisUtils.close(jedis);
	}

	private static String getToken(String username) {
		Jedis jedis = JedisUtils.getJedis();
		jedis.select(2);
		String token = jedis.get(username);
		JedisUtils.close(jedis);
		return token;
	}

	// 设置失效时间
	private static long getExSecond() {

		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, date.getYear());
		c.set(Calendar.MONTH, date.getMonth());
		int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		long second = getSecond(days);
		return second;
	}

	// 拿到距离本月月底的秒数
	private static long getSecond(int day) {

		// 当前时间
		Date date = new Date();
		long day1 = date.getDate();
		long hour = date.getHours();
		long min = date.getMinutes();
		long ss = date.getSeconds();

		// 本月-当前
		return (day - day1) * 60 * 60 * 24 + (24 - hour) * 60 * 60 + (00 - min)
				* 60 + (00 - ss);
	}

}
