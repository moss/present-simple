package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.Weaver;
import net.m14m.presentsimple.aspects.AspectRegistry;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class CglibWeaver implements Weaver {
    private final AspectRegistry aspectRegistry = new AspectRegistry();

    public void register(Decorator decorator) {
        aspectRegistry.register(decorator);
    }

    public <T> T createInstance(Class<T> targetClass) {
        try {
            return decorateClass(targetClass).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked"})
    public <T> Class<? extends T> decorateClass(Class<T> targetClass) {
        AspectInterceptor interceptor = new AspectInterceptor(aspectRegistry.aspectArray());
        Enhancer enhancer = new Enhancer();
        enhancer.setCallbackType(AspectInterceptor.class);
        enhancer.setSuperclass(targetClass);
        Class<? extends T> enhancedClass = enhancer.createClass();
        Enhancer.registerCallbacks(enhancedClass, new Callback[]{interceptor});
        return enhancedClass;
    }
}
