import com.summer.icommon.utils.JwtUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

//import org.apache.shiro.crypto.SecureRandomNumberGenerator;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.apache.shiro.crypto.hash.Sha256Hash;
//
public class test {

    public static  void main(String [] args){
                    //生成盐（部分，需要存入数据库中）
                String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
//        System.out.println("2:"+random);
//                //将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
//        SecureRandomNumberGenerator secureRandomNumberGenerator=new SecureRandomNumberGenerator();
//        String salt= secureRandomNumberGenerator.nextBytes().toHex();
               String result = new Md5Hash("123456",ByteSource.Util.bytes(salt) ,1).toHex();
        System.out.println("1:"+result+"22'"+salt);
//
//        String random=  JwtUtils.generateSalt();
//        System.out.println("1"+random);
//        String aa = new Sha256Hash("123456", random).toHex();
//        System.out.println("2"+aa);
           String a = "roles";
           String b = "[";
           String c = "admin";
           String d =  "]";
           a = a+b+c+d;
           StringBuilder sb = new StringBuilder();
           sb.append("roles").append("[").append(c).append("]");
        System.out.println("1"+a);
        System.out.println("sb"+sb);

    }
}
