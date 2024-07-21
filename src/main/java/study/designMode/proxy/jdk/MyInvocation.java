package study.designMode.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocation implements InvocationHandler {
    private Object target;

    public MyInvocation(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("说hello前先说hi");
        method.invoke(target);
        System.out.println("说完hello后再说自己的名字");
        return null;
    }
}
