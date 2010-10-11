package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Aspect;
import net.m14m.presentsimple.MethodInvocation;
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
        MethodInvocation invocation = new ProxiedMethodInvocation(delegate, method, args, proxy);
        Aspect aspect = aspects[0];
        if (!aspect.getPointcut().matches(method)) return invocation.invoke();
        return new AdviceDecoratedMethodInvocation(invocation, aspect).invoke();
    }

    private class AdviceDecoratedMethodInvocation implements MethodInvocation {
        private MethodInvocation invocation;
        private Aspect aspect;

        public AdviceDecoratedMethodInvocation(MethodInvocation invocation, Aspect aspect) {
            this.invocation = invocation;
            this.aspect = aspect;
        }

        public Object getReceiver() {
            return null;
        }

        public Method getMethod() {
            return null;
        }

        public Object[] getArguments() {
            return new Object[0];
        }

        public Object invoke() throws Throwable {
            return aspect.getAdvice().advise(invocation);
        }
    }
}
