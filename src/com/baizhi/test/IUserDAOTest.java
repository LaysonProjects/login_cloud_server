package com.baizhi.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baizhi.dao.IUserDAO;
import com.baizhi.entity.BasicUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class IUserDAOTest {

	@Autowired
	private IUserDAO userDao;

	public IUserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDAO userDao) {
		this.userDao = userDao;
	}

	@Test
	public void testLogin() {
		BasicUser login = userDao.login("610224874@qq.com", "123456");
		System.out.println(login);
	}

}
