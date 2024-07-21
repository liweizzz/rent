package study.designMode.proxy.cjlib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("先说hello吧");
        //调用目标类的方法
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("处理完毕");
        return result;
    }
}
