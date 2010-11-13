package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.MethodCall;
import net.m14m.presentsimple.aspects.Aspect;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AspectInterceptor implements MethodInterceptor {
    private final Aspect[] aspects;

    public AspectInterceptor(Aspect... aspects) {
        this.aspects = aspects;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodCall call = new ProxiedMethodCall(obj, method, args, proxy);
        for (Aspect aspect : aspects) {
            call = decorate(call, aspect);
        }
        return call.invoke();
    }

    private MethodCall decorate(MethodCall call, Aspect aspect) {
        if (!aspect.pointcutMatches(call)) return call;
        return new DecoratedMethodCall(call, aspect.getDecorator());
    }

}
