package com.baizhi.framework.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	private static JedisPool jedisPool;
	static {
		JedisPoolConfig jedisConfig = new JedisPoolConfig();
		jedisConfig.setMaxTotal(100);
		jedisConfig.setMaxIdle(20);
		jedisConfig.setMaxWaitMillis(100);

		jedisPool = new JedisPool(jedisConfig, "192.168.134.30", 6379);
	}

	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	public static void close(Jedis jedis) {
		jedis.close();
	}
}
