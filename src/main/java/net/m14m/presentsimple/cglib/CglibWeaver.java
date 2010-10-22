package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.AppliesTo;
import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.Weaver;
import net.m14m.presentsimple.pointcuts.AnnotatedPointcut;
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

    @SuppressWarnings({"unchecked"})
    public <T> T decorate(T object) {
        AspectApplyingMethodInterceptor interceptor = new AspectApplyingMethodInterceptor(object, arrayOfAspects());
        return (T) Enhancer.create(object.getClass(), interceptor);
    }

    private Aspect[] arrayOfAspects() {
        return aspects.toArray(new Aspect[aspects.size()]);
    }
}
