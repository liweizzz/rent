package study.designMode.singleton;

/**
 * 懒汉式线程安全
 */
public class Singleton {
    private static Singleton instance;

    private Singleton(){

    }

    public synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

}

/**
 * 饿汉式
 */
class Singleton1 {
    private static Singleton1 singleton1 = new Singleton1();
    private Singleton1(){}
    public static Singleton1 getInstance(){
        return singleton1;
    }
}

/**
 * 双重锁 （属于懒汉式）
 * 第一次检查：判断是否存在，存在就不必进入同步代码块了
 * 第二次检查：多个线程在排队进入同步代码块时，第一个线程先获取锁并创建对象，那第二个线程进去如果不判断则会重复创建对象
 * volatile 关键之保证了内存可见，防止指令重排
 */
class Singleton2 {
    private static volatile Singleton2 singleton2;
    private Singleton2(){

    }
    public static Singleton2 getInstance(){
        //singleton2为空时，则获取类锁，不是的话就直接返回对象
        if(singleton2 == null){
            synchronized (Singleton2.class){
                if(singleton2 == null){
                    singleton2 = new Singleton2();
                }
            }
        }
        return singleton2;
    }
}

/**
 * 枚举实现最简单，枚举在jvm中只存在一份，所以是单例，属于饿汉式中的一种
 * 枚举可以实现接口来对枚举进行增强,不能继承是因为已经隐式的继承了java.lang.Enum
 */
enum Singleton3 {
    INSTANCE;
    public void getInstance(){

    }
}
