import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author liudongting
 * @date 2019/7/9 14:39
 */
public class test {
    public static void main(String[] args) {
        //水仙花数
//        for (int a = 1; a <= 9 ; a++) {
//            for(int b = 0;b <= 9;b++){
//                for(int c = 0;c <= 9;c++){
//                     if((a*a*a+b*b*b+c*c*c)==(a*100+b*10+c)){
//                         System.out.println("水仙花数:"+"--"+(a*100+b*10+c));
//                     }
//
//                }
//            }
//        }
//        int [] s = {1, 6, 2 ,7 , 3,8 ,4 ,9 ,5,10};
//        int [] a = new int[10000];
//        List<Integer> aa= new ArrayList<Integer>(1000);
//        for(Integer ss : s){
//            aa.add(ss);
//        }
//        int b= s.length;
//        for (int i = 0; i < b; i++) {
//            aa.remove(s[i]);
//            aa.remove(s[i+1]);
//            aa.add(s[i+1]);
//            if(i==s[i]){
//                System.out.println("第十个数为:"+s[s.length-1]);
//            }
//            b-=1;
//        }
//        for (int i = 0; i < aa.size(); i++) {
//            System.out.println("aa:"+i+"---"+aa.get(i));
//        }
        System.out.print("指定最大位数N:");
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        input.close();
        for (int i = 3; i <= N; i++) {
            int a[] = new int[i];
            int num = (int) Math.pow(10, i - 1) + 1;
            System.out.print(i + "位的水仙花数有：\t");
            while (num <= Math.pow(10, i)) {
                int sum = 0;
                for (int j = 0; j < i; j++)
                    a[j] = (int) (num / Math.pow(10, j) % 10);
                for (int j = 0; j < i; j++)
                    sum = sum + (int) Math.pow(a[j], i);
                if (num == sum)
                    System.out.print(num + "\t");
                num++;
            }
            System.out.print("\n");
        }
    }

}
