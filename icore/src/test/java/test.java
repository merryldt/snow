import com.summer.icommon.utils.JwtUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

public class test {

    public static  void main(String [] args){
                    //生成盐（部分，需要存入数据库中）
//                String random=new SecureRandomNumberGenerator().nextBytes(16).toHex();
//        System.out.println("2:"+random);
//                //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
//                String result = new Md5Hash("123456",random,3).toString();
//        System.out.println("1:"+result);

        String random=  JwtUtils.generateSalt();
        System.out.println("1"+random);
        String aa = new Sha256Hash("123456", random).toHex();
        System.out.println("2"+aa);
    }
}
