import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liudongting
 * @date 2019/7/10 12:09
 */
public class tee12 {
    static Set<String> lists = new LinkedHashSet<>();
    public static void main(String[] args) {
        String[] color = { "a", "b"};
        List<String[]> list = new ArrayList<String[]>();
        list.add(color);
        for( int i=0;i<11;i++){
            switch (i){
                case 0 : String[] colors = { "a", "b"};
                              list.add(colors);
                              break;
                case 1 : String[] colors1 = { "a", "b"};
                              list.add(colors1);
                               break;
                case 2 : String[] colors2 = { "a", "b"};
                    list.add(colors2);
                    break;
                case 3 : String[] colors3 = { "a", "b"};
                    list.add(colors3);
                    break;
                case 4 : String[] colors4 = { "a", "b"};
                    list.add(colors4);
                    break;
                case 5 : String[] colors5 = { "a", "b"};
                    list.add(colors5);
                    break;
                case 6 : String[] colors6 = { "a", "b"};
                    list.add(colors6);
                case 7 : String[] colors7 = { "a", "b"};
                    list.add(colors7);
                    break;
                case 8 : String[] colors8 = { "a", "b"};
                    list.add(colors8);
                    break;
                case 9 : String[] colors9 = { "a", "b"};
                    list.add(colors9);
                    break;
            }
        }
        sorting (list, color, "");
        System.out.println("ss'"+lists.size());

//        int num = 0;
//        for (int i = 0; i < lists.size(); i++) {
//            int x = 0;
//            int y = 0;
//            String []  bbb = lists..split(",");
//            for (int f = 0; f <bbb.length ; f++) {
//                if(bbb[f].equals("a")){
//                    x+=1;
//                }else if(bbb[f].equals("b"))
//                    y+=1;
//                if(x>y){
//                    num+=1;
//                    break;
//                }
//            }
//
//        }
//        System.out.println("ss'"+lists.size()+"---"+lists);
//        System.out.println("ss'"+num);
    }

    public static void sorting (List<String[]> list, String[] arrs, String str) {
        int aa = 0;
        for (int i = 0; i < list.size(); i++) {
            //取得当前的数组
            if (i == list.indexOf(arrs)) {
                //迭代数组
                for (String arr : arrs) {
                    arr = str + arr;
                    if(i < list.size() - 1){
                        arr = arr + ",";
                        sorting (list, list.get(i + 1), arr);
                    } else if (i == list.size() - 1) {
                        aa+=1;
                        System.out.println(arr);
                        lists.add(arr);
                    }
                }
            }
        }
    }

}
