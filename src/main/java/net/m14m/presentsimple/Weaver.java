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
        for (Aspect aspect : aspects) {
            object = decorateObjectWith(aspect, object);
        }
        return object;
    }

    private <T> T decorateObjectWith(final Aspect aspect, final T object) {
        Enhancer enhancer = new Enhancer();
        return (T) enhancer.create(object.getClass(), new AspectApplyingMethodInterceptor(aspect, object));
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
            return proxy.invoke(object, args);
        }

        public Method getMethod() {
            return method;
        }
    }

    private static class AspectApplyingMethodInterceptor implements MethodInterceptor {
        private final Aspect aspect;
        private final Object object;

        public AspectApplyingMethodInterceptor(Aspect aspect, Object object) {
            this.aspect = aspect;
            this.object = object;
        }

        public Object intercept(Object obj, Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
            if (annotated(method)) return aspect.intercept(new ProxiedMethodInvocation(proxy, method, object, args));
            return proxy.invoke(object, args);
        }

        private boolean annotated(Method method) {
            return method.isAnnotationPresent(aspect.getAnnotation());
        }
    }
}
