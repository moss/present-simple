package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Aspect;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AspectApplyingMethodInterceptor implements MethodInterceptor {
    private final Object delegate;
    private final Aspect aspect;

    public AspectApplyingMethodInterceptor(Object delegate, Aspect aspect) {
        this.delegate = delegate;
        this.aspect = aspect;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!aspect.getPointcut().matches(method)) return proxy.invoke(delegate, args);
        return aspect.getAdvice().advise(new ProxiedMethodInvocation(delegate, method, args, proxy));
    }
}
