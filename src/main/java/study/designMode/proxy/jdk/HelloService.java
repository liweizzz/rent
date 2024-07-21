package study.designMode.proxy.jdk;

public class HelloService implements IHelloService{
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
