import java.util.HashMap;
import java.util.Map;

/**
 * @author liudongting
 * @date 2019/7/11 17:42
 */
public class test4 {
    public static void main(String[] args) {
//        int aa = cale(6,6,false);
//        System.out.println("aaa'"+aa);
        StringBuffer sb = new StringBuffer("未阅邮件,通知公告,个人通讯录,单位通讯录,单位通讯录(按条件搜索),负责人通讯录,负责人通讯录(按条件搜索),内部邮件,领导交办,公文流转,文件柜,个人网盘,日程管理,新建公文");
        sb.substring(0,4);
        System.out.println(sb.substring(0,4));
        Map<String,String> map = new HashMap<>();
        if(!sb.substring(0,4).equals("未阅邮件")){
            map.put("name",sb+"");
        }
        System.out.println(sb);

    }
    public  static  Integer cale(Integer black,Integer white,boolean flag){
         if(black>white){
             flag = true ;
         }else  flag=flag;
         if(white==0 || black ==0){
             return flag ? 1:0;
         }
         Integer a = cale(black,white-1,flag);
        System.out.println("a:"+a);
         Integer b = cale(black-1,white,flag);
        System.out.println("b:"+b);
         return a+b;
    }
}
