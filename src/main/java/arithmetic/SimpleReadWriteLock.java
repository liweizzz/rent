package arithmetic;

public class SimpleReadWriteLock {
    private volatile boolean writeLocked = false;

    public void lockWrite() {
        while (writeLocked) {
            // 自旋等待，直到 writeLocked 变为 false
        }
        writeLocked = true;
    }

    public void unlockWrite() {
        writeLocked = false;
    }

    public void readOperation() {
        // 执行读操作，无需加锁
    }

    public void writeOperation() {
        lockWrite();
        try {
            System.out.println("haha");
            // 执行写操作
        } finally {
            unlockWrite();
        }
    }

    public static void main(String[] args) {
        SimpleReadWriteLock lock = new SimpleReadWriteLock();
        lock.writeOperation();
    }
}
