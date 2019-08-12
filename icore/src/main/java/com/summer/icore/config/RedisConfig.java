package com.summer.icore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import redis.clients.jedis.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Order(1)
@Configuration
public class RedisConfig {

    private final static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    //声明静态的JedisPool属性
    private static JedisPool jedisPool = null;
    private static JedisCluster cluster = null;
    public static String redisHost;
    public static Integer redisPort;
    public static String password;
    public static Integer timeout;
    private static  Integer database;

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
            timeout  = Integer.parseInt(properties.getProperty("spring.redis.timeout"));
            database  = Integer.parseInt(properties.getProperty("spring.redis.database"));
            //创建JedisPoolConfig连接池配置对象
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(8);
            poolConfig.setMaxIdle(8);
            poolConfig.setMinIdle(1);
            // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
            // Could not get a resource from the pool
            //最大建立连接等待时间
            poolConfig.setMaxWaitMillis(10000);
            //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
//            poolConfig.setMinEvictableIdleTimeMillis(300000);
//            //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
//            poolConfig.setNumTestsPerEvictionRun(10);
//            //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
//            poolConfig.setTimeBetweenEvictionRunsMillis(30000);
//            //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
//            poolConfig.setTestOnBorrow(true);
//            //在空闲时检查有效性, 默认false
//            poolConfig.setTestWhileIdle(true);

            jedisPool = new JedisPool(poolConfig, redisHost,redisPort,timeout);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("get properties is faile",e);
        }
    }

    /**
     * 集群配置
     */
//    static {
//        //创建JedisPoolConfig连接池配置对象
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
//        nodes.add(new HostAndPort("47.100.2.226", 6379));
//        nodes.add(new HostAndPort("47.100.2.226", 6380));
//        cluster = new JedisCluster(nodes, poolConfig);
//    }
    /**
     * 得到redis操作资源对象方法
     * @return Jedis操作资源对象
     */
    public synchronized static Jedis getJedis() {
        Jedis resource = null;
        if (jedisPool != null) {
            //获取资源
            try {
                resource = jedisPool.getResource();
                resource.auth(password);
                resource.select(database);
            }catch (Exception e){
               e.printStackTrace();
                logger.error("get jedis faile ",e);
            }
        }
        return resource;
    }
    /**
     * 释放资源
     * jedis连接用完要释放即close，如果不close，
     * 则产生的连接会越来越多，当达到了最大连接数，再想获得连接，就会等待，当超过了最大等待时间后就会报异常。
     */
    public static void returnJedis(Jedis jedis) {
        if (jedis != null) {
            //释放资源
            jedis.close();
        }
    }


}
