package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.MethodInvocation;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AspectApplyingMethodInterceptor implements MethodInterceptor {
    private final Object delegate;
    private final AspectInvocationEnhancer[] enhancers;

    public AspectApplyingMethodInterceptor(Object delegate, AspectInvocationEnhancer... enhancers) {
        this.delegate = delegate;
        this.enhancers = enhancers;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodInvocation invocation = new ProxiedMethodInvocation(delegate, method, args, proxy);
        for (AspectInvocationEnhancer enhancer : enhancers) {
            invocation = enhancer.wrap(invocation);
        }
        return invocation.invoke();
    }

}
