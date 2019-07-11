/**
 * @author liudongting
 * @date 2019/7/11 17:42
 */
public class test4 {
    public static void main(String[] args) {
        int aa = cale(6,6,false);
        System.out.println("aaa'"+aa);
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
