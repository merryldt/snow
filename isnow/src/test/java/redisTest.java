
import com.summer.icore.config.RedisConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.List;

/**
 * @author liudongting
 * @date 2019/7/16 10:29
 */
public class redisTest {



    @Test
    public void test1(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
//      JedisPool  jedisPool = new JedisPool(poolConfig, "47.100.2.226",6366);
//        Jedis jedis =  jedisPool.getResource();
        Jedis  jedis =  RedisConfig.getJedis();
        if(jedis.isConnected()){
            System.out.println("successful");
        }
    }
    @Test
    public  void test3(){
        int i=1;
        int j=i++;        // i 和j  在这里都是1 ，所以是一个对象 拆箱
//        System.out.println(System.identityHashCode(i));
//        System.out.println(System.identityHashCode(i++));
//        System.out.println(System.identityHashCode(++j));
        if( i==(j++)&&( i++) == j){
                i+=j;
        }
        System.out.println("i:"+ i);
    }
    @Test
    public void test4(){
//        List<Integer> list=Arrays.asList(1,2,3,4); //如果在JDK 1.7中，还有另外一颗语法糖[1] //能让上面这句代码进一步简写成

        List<Integer> list = Arrays.asList(1,2,3,4);
        int sum=0;
        for(int i:list){
            sum+=i;
        }
        System.out.println(sum);
    }



}
