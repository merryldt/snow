package java_base;

/**
 * @author liudongting
 * @date 2019/8/5 14:12
 */
public class Synchronized_test implements Runnable {

    private static int count;
    public Synchronized_test() {
        count = 0;
    }
    public  synchronized static void method() {
        for (int i = 0; i < 5; i ++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public synchronized void run() {
        method();
    }

    public static void main(String[] args) {
        Synchronized_test synchronized_test = new Synchronized_test();
        Synchronized_test synchronized_test2 = new Synchronized_test();
        Thread thread = new Thread(synchronized_test,"synchronized_test");
        Thread thread2 = new Thread(synchronized_test2,"synchronized_test2");
        thread.start();
        thread2.start();
    }
}
