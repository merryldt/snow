import com.summer.isnow.utils.ShiroKit;
import org.junit.Test;

/**
 * @author liudongting
 * @date 2019/7/16 10:29
 */
public class redisTest2 {



    @Test
    public void test1(){
        String passwords = ShiroKit.md5("123456","test"+"a910ed7a0b683a94c7b6824ca25f6131");
        System.out.println("sss':"+passwords);
    }
}
