package study.thread;

/**
 * 设计两个线程，依次交替执行a+1，直到a=100为止，并且输出当前的线程名和a的值
 */
public class Counter {
    private int a;
    private final Object lock = new Object();
    private boolean flag = false;

    public void increaseByThread1(){
        synchronized (lock){
            while (a <100){
                if(flag){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(a<100){
                    a++;
                    flag = true;
                    System.out.println("Thread name :" + Thread.currentThread().getName() + "  a:"+a);
                    lock.notify();
                }
            }
        }
    }

    public void increaseByThread2(){
        synchronized (lock) {
            while (a<100){
                if(!flag){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(a<100){
                    a++;
                    flag = false;
                    System.out.println("Thread name :" + Thread.currentThread().getName() + "  a:"+a);
                    lock.notify();
                }
            }
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(counter::increaseByThread1);
        Thread thread2 = new Thread(counter::increaseByThread2);
        thread1.start();
        thread2.start();
    }
}
