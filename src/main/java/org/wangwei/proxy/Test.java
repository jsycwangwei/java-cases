package org.wangwei.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        Hello h = new HelloImpl();
        InvocationHandler hanlder = new DoProxy(h);
        Hello hp = (Hello) Proxy.newProxyInstance(h.getClass().getClassLoader(), h.getClass().getInterfaces(), hanlder);
        hp.say();
    }
}
