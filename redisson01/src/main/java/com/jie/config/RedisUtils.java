package com.jie.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(20);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.23.131",6379);
    }

    public static Jedis getJedis() throws Exception {
        if(null != jedisPool){
            return jedisPool.getResource();
        }
        throw new Exception("jedispool is not ok");
    }
}
