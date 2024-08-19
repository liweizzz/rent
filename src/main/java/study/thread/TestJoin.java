package study.thread;

public class TestJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> {
            System.out.println("one");
        });

        Thread two = new Thread(() -> {
            try {
                one.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("two");
        });

        Thread three = new Thread(() -> {
            try {
                two.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("three");
        });

        one.start();
        two.start();
        three.start();

        System.out.println("hjaha");
    }
}
