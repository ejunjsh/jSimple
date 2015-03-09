

public class WaitAndNotify {

    public static void main(String[] args) throws InterruptedException {
        WaitAndNotify test = new WaitAndNotify();
        WaitNotifyObject obj = test.new WaitNotifyObject();
        Thread t1 = new Thread(test.new TestThread1(obj));
        Thread t2 = new Thread(test.new TestThread2(obj));

        t1.start();
        Thread.sleep(1000);
        t2.start();

        //synchronized(this) 表示监控调用方方法的本身

    }

    class MonitorObject {

    }

    class TestThread1 implements Runnable {
        WaitNotifyObject object;

        TestThread1(WaitNotifyObject obj) {
            this.object = obj;
        }

        @Override
        public void run() {
            this.object.doWait();
        }
    }

    class TestThread2 implements Runnable {
        WaitNotifyObject object;

        TestThread2(WaitNotifyObject obj) {
            this.object = obj;
        }

        @Override
        public void run() {
            this.object.doNotify();
        }
    }

    class WaitNotifyObject {
        MonitorObject myMonitorObject = new MonitorObject();
        boolean wasSignalled = false; //避免假唤醒的情况

        public void doWait() {
            synchronized (myMonitorObject) {
                while (!wasSignalled) {
                    try {
                        System.out.println("WaitNotifyObject wait!!");
                        // 一个线程一旦调用了任意对象的wait()方法，就会变为非运行状态，直到另一个线程调用了同一个对象的notify()方法
                        myMonitorObject.wait();

                        //唤醒后会继续从wait()地方开始执行
                        System.out.println("WaitNotifyObject wait over!!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                wasSignalled = true;
            }
        }

        public void doNotify() {
            synchronized (myMonitorObject) {
                wasSignalled = true;
                System.out.println("WaitNotifyObject notify!!");
                myMonitorObject.notify();
            }
        }

    }

}
