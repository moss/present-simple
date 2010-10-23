package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.AppliesTo;
import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.Weaver;
import net.m14m.presentsimple.pointcuts.AnnotatedPointcut;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class CglibWeaver implements Weaver {
    private List<Aspect> aspects = new ArrayList<Aspect>();

    public void register(Decorator decorator) {
        aspects.add(new Aspect(new AnnotatedPointcut(findAnnotation(decorator)), decorator));
    }

    private Class<? extends Annotation> findAnnotation(Decorator decorator) {
        return decorator.getClass().getAnnotation(AppliesTo.class).value();
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
        AspectApplyingMethodInterceptor interceptor = new AspectApplyingMethodInterceptor(arrayOfAspects());
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallbackType(AspectApplyingMethodInterceptor.class);
        Class<? extends T> enhancedClass = enhancer.createClass();
        Enhancer.registerCallbacks(enhancedClass, new Callback[]{interceptor});
        return enhancedClass;
    }

    private Aspect[] arrayOfAspects() {
        return aspects.toArray(new Aspect[aspects.size()]);
    }
}
