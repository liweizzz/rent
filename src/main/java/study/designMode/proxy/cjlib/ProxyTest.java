package study.designMode.proxy.cjlib;

import org.springframework.cglib.proxy.Enhancer;

public class ProxyTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloService.class);
        enhancer.setCallback(new MyInterceptor());
        HelloService proxy = (HelloService)enhancer.create();
        proxy.sayHi();
    }
}
