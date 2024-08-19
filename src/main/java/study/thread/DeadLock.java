package study.thread;

/**
 * 两个线程相互等待对方释放对象锁
 */
public class DeadLock {
    private final Object lock1 = new Object();

    private final Object lock2 = new Object();

    public void sss(){
        synchronized (lock1){
            try {
                System.out.println("获取到线程1，等到获取线程2...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                System.out.println("获取到线程2");
            }
        }
    }

    public void s(){
        synchronized (lock2){
            try {
                System.out.println("获取到线程2，等到获取线程1...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock1) {
                System.out.println("获取到线程1");
            }
        }
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        Thread thread1 = new Thread(deadLock::sss);
        Thread thread2 = new Thread(deadLock::s);
        thread1.start();
        thread2.start();
    }
}
