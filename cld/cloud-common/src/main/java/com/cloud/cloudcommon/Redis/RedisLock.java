package com.cloud.cloudcommon.Redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by 乔航 on 2019/7/8.
 */
public class RedisLock implements Lock {

    private Jedis jedis;
    private ThreadLocal<String> threadLocal=new ThreadLocal<>();

    private String LOCK = "lock";

    public RedisLock(String lock) {
        this.LOCK=lock;
    }

    public RedisLock(){

    }

    @Override
    public void lock() {
        if(tryLock()){
            return;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock();
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        jedis=RedisUtil.getConnection();
        //时如将生成的随机uuid写入lock键，ok上锁成功，否则失败
        String uuid = UUID.randomUUID().toString();
        String res = jedis.set(LOCK, uuid, "NX", "EX", 10);
        if("OK".equals(res)){
            threadLocal.set(uuid);
            return  true;
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";// lua脚本，用来释放分布式
        //凭证相同才可以释放锁（删除lock）
        Long res = (Long) jedis.eval(luaScript, Collections.singletonList(LOCK),Collections.singletonList(threadLocal.get()));
        if(jedis!=null){
            jedis.close();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
