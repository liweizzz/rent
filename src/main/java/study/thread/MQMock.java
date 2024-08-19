package study.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用blockingqueue模拟生产消费
 */
public class MQMock {
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

    public static void product(){
        for (int i = 0; i < 10; i++) {
            try {
                queue.put("a");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void consumer(){
        for (int i = 0; i < 10; i++) {
            String poll = null;
            try {
                poll = queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(poll);
        }
    }

    public static void main(String[] args) {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
//        for (int i = 0; i < 2; i++) {
//            threadPoolExecutor.execute(()->MQMock.product());
//        }
        new Thread(MQMock::product).start();
        new Thread(MQMock::consumer).start();
    }
}
