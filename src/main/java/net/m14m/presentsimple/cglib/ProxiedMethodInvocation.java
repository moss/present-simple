package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.MethodInvocation;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxiedMethodInvocation implements MethodInvocation {
    private final Object receiver;
    private final Method method;
    private final Object[] arguments;
    private final MethodProxy proxy;

    public ProxiedMethodInvocation(Object receiver, Method method, Object[] arguments, MethodProxy proxy) {
        this.receiver = receiver;
        this.method = method;
        this.arguments = arguments;
        this.proxy = proxy;
    }

    public Object getReceiver() {
        return receiver;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Object invoke() throws Throwable {
        return proxy.invokeSuper(receiver, arguments);
    }
}
