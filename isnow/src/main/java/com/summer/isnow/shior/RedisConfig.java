package com.summer.isnow.shior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class RedisConfig {

    private final static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    //声明静态的JedisPool属性
    private static JedisPool jedisPool = null;
    public static String redisHost;
    public static Integer redisPort;
    public static String password;
    static {
        try {
            //获取当前类加载器
            ClassLoader classLoader = RedisConfig.class.getClassLoader();
            //通过当前累加载器方法获得 文件db.properties的一个输入流
            InputStream is = classLoader.getResourceAsStream("application.properties");
            //创建一个Properties 对象
            Properties properties = new Properties();
            //加载输入流
            properties.load(is);

            redisHost = properties.getProperty("spring.redis.host");
            redisPort = Integer.parseInt(properties.getProperty("spring.redis.port"));
            password  = properties.getProperty("spring.redis.password");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("get properties is faile",e);
        }
    }
    //静态块
    static{
        //创建JedisPoolConfig连接池配置对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(1);
        //创建redis连接池，把配置对象给她
        jedisPool = new JedisPool(poolConfig, redisHost,redisPort);
    }
    /**
     * 得到redis操作资源对象方法
     * @return Jedis操作资源对象
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
    @Bean
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool(RedisProperties redisProperties) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        return new JedisPool(poolConfig, redisProperties.getHost(), redisProperties.getPort(), redisProperties.getTimeout(),
                redisProperties.getPassword());
    }
}
