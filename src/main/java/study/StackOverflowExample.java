package study;

public class StackOverflowExample {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            byte[] array = new byte[1024 * 1024]; // 创建1MB大小的对象
        }
    }

    private static int depth = 0;

    public static void recursiveMethod(int level) {
        depth = level;
        recursiveMethod(level + 1);
    }
}
