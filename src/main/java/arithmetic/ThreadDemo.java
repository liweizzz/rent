package arithmetic;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public class ThreadDemo {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(16);

    public void solution(){
    }

}
class ThreadSafeFormater{
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(new Supplier<SimpleDateFormat>() {
        @Override
        public SimpleDateFormat get() {
            return new SimpleDateFormat("mm:ss");
        }
    });
}
