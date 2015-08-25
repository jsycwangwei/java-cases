package org.wangwei.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DoProxy implements InvocationHandler {

    private Object obj;

    public DoProxy(Object obj) {
        super();
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        doBefore();
        result = method.invoke(obj, args);
        doAfter();

        return result;
    }

    private void doBefore() {
        System.out.println("do before");
    }

    private void doAfter() {
        System.out.println("do after");
    }

}
