package thirdparty.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author chengzheng
 * @Date 2017/8/6
 * @TIME 上午9:24
 */
public class RedisPoolManager {

    private static final Logger log = LoggerFactory.getLogger(RedisPoolManager.class);

    public String REDIS_SERVER = "localhost";

    public int REDIS_PORT = 6666;

    private JedisPool jedisPool = null;

    private JedisPool getInstance(){

        if(jedisPool == null){

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(1000);
            config.setMaxTotal(20);
            config.setMaxWaitMillis(10*1000);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            jedisPool = new JedisPool(config,REDIS_SERVER,REDIS_PORT);
        }

        return jedisPool;
    }

    /**
     * 获取jedis
     *
     * @return
     */
    public Jedis getJedis(){

        Jedis jedis = null;

        try{
            jedis = getInstance().getResource();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return jedis;
    }


    /**
     * 返回jedis
     *
     * @param jedis
     */

    public void returnJedis(Jedis jedis){

        try {

            if(jedis != null){
                getInstance().returnResource(jedis);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }


    /**
     * returnBrokenJedis
     *
     * @param jedis
     */
    public void returnBrokenJedis(Jedis jedis){

        try{

            if(jedis != null){
                getInstance().returnBrokenResource(jedis);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }


    /**
     * 释放jedis
     *
     * @param jedis
     */
    public void releaseJedis(Jedis jedis){
        jedisPool.returnResource(jedis);

    }


}
