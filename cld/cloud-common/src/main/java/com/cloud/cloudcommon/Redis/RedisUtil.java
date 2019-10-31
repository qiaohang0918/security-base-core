package com.cloud.cloudcommon.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by 乔航 on 2019/7/8.
 */
public class RedisUtil {
    //redis服务地址
    public static final String REDIS_IP="192.168.111.129";
    //jedis配置
    public static JedisPoolConfig jedisPoolConfig=null;
    //jedisPool连接池
    public static  JedisPool jedisPool=null;
    //jedis链接
    private static Jedis jedis=null;

    //初始化配置和连接池
   static {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(3000);
        jedisPoolConfig.setMaxIdle(50);
        jedisPool = new JedisPool(jedisPoolConfig,REDIS_IP);
    }

    /**
     * 获取jedis链接
     * @return
     */
    public static Jedis  getConnection(){
        jedis = jedisPool.getResource();
        return jedis;
    }

    /**
     *关闭jedsi链接
     */
    public static void closeJedisConnection(){
        if(jedis!=null){
            jedis.close();
            jedis=null;
        }
    }

}
