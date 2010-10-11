package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Aspect;
import net.m14m.presentsimple.MethodInvocation;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AspectApplyingMethodInterceptor implements MethodInterceptor {
    private final Object delegate;
    private final AspectInvocationEnhancer[] enhancers;

    public AspectApplyingMethodInterceptor(Object delegate, Aspect... aspects) {
        this.delegate = delegate;
        enhancers = new AspectInvocationEnhancer[aspects.length];
        for (int i = 0; i < aspects.length; i++) {
            enhancers[i] = new AspectInvocationEnhancer(aspects[i]);
        }
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodInvocation invocation = new ProxiedMethodInvocation(delegate, method, args, proxy);
        for (AspectInvocationEnhancer enhancer : enhancers) {
            invocation = enhancer.wrap(invocation);
        }
        return invocation.invoke();
    }

}
