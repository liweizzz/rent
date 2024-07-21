package study.designMode.proxy.jdk;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        HelloService helloService = new HelloService();
        MyInvocation myInvocation = new MyInvocation(helloService);
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        IHelloService instance = (IHelloService)Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class[]{IHelloService.class}, myInvocation);
        instance.sayHello();
    }
}
