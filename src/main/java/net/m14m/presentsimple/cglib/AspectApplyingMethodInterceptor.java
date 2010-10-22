package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.MethodCall;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AspectApplyingMethodInterceptor implements MethodInterceptor {
    private final Object delegate;
    private final Aspect[] aspects;

    public AspectApplyingMethodInterceptor(Object delegate, Aspect... aspects) {
        this.delegate = delegate;
        this.aspects = aspects;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodCall call = new ProxiedMethodCall(delegate, method, args, proxy);
        for (Aspect aspect : aspects) {
            call = aspect.decorate(call);
        }
        return call.invoke();
    }

}
