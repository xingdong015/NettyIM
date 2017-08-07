package thirdparty.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chengzheng
 * @Date 2017/8/6
 * @TIME 上午9:22
 */

//TODO 为防止并发稍后加入对数据库的支持
public class RedisOperator {

    private static final Logger log = LoggerFactory.getLogger(RedisOperator.class);

    //单个数据库的读取和写入目前可以调用hset和hget
}
