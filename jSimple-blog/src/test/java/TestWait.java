import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shaojunjie on 2015/1/19.
 */
public class TestWait {

    public static void main(String[] args) {
        AThread a = new AThread();
        BThread b = new BThread();
        Thread thread = new Thread(a);
        thread.start();
        Thread thread1 = new Thread(a);
        thread1.start();
        Thread thread2 = new Thread(b);
        thread2.start();

        ExecutorService executor = Executors.newFixedThreadPool(11);

        for (int i = 0; i < 12; i++) {
            final int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(finalI + "");
                    }
                }
            });
        }
    }


    public static class AThread implements Runnable {

        @Override
        public void run() {

            System.out.println("AThread is going to run.");
            try {
                synchronized (AThread.class) {
                    AThread.class.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("AThread is over.");
        }
    }

    public static class BThread implements Runnable {

        @Override
        public void run() {

            System.out.println("BThread is going to run.");

            synchronized (AThread.class) {
                AThread.class.notifyAll();
            }

            System.out.println("BThread is over.");
        }
    }
}
