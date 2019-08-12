import java.util.HashMap;
import java.util.Map;

/**
 * @author liudongting
 * @date 2019/7/26 10:28
 */
public class test5 {
    public static void main(String[] args) {
        Map<String,String> map =new HashMap<>();
        if (null != map && map.containsKey("userName")) {
            map.remove("userName");
        }

    }
}
