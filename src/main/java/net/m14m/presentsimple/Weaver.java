package net.m14m.presentsimple;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Weaver {
    private final Aspect[] aspects;

    public Weaver(Aspect... aspects) {
        this.aspects = aspects;
    }

    public <T> T weaveAspectsInto(T object) {
        return decorateObjectWith(aspects[0], object);
    }

    private <T> T decorateObjectWith(final Aspect aspect, final T object) {
        return (T) Enhancer.create(object.getClass(), null, new AspectApplyingMethodInterceptor(aspect));
    }

    private static class ProxiedMethodInvocation implements MethodInvocation {
        private final MethodProxy proxy;
        private final Method method;
        private final Object object;
        private final Object[] args;

        public ProxiedMethodInvocation(MethodProxy proxy, Method method, Object object, Object[] args) {
            this.proxy = proxy;
            this.method = method;
            this.object = object;
            this.args = args;
        }

        public Object invoke() throws Throwable {
            return proxy.invokeSuper(object, args);
        }

        public Method getMethod() {
            return method;
        }
    }

    private static class AspectApplyingMethodInterceptor implements MethodInterceptor {
        private final Aspect aspect;

        public AspectApplyingMethodInterceptor(Aspect aspect) {
            this.aspect = aspect;
        }

        public Object intercept(Object object, Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
            if (annotated(method)) return aspect.intercept(new ProxiedMethodInvocation(proxy, method, object, args));
            return proxy.invokeSuper(object, args);
        }

        private boolean annotated(Method method) {
            return method.isAnnotationPresent(aspect.getAnnotation());
        }
    }
}
