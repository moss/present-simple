package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Advice;
import net.m14m.presentsimple.Aspect;
import net.m14m.presentsimple.SimpleAspect;
import net.m14m.presentsimple.Weaver;
import net.m14m.presentsimple.pointcuts.AnnotatedPointcut;
import net.sf.cglib.proxy.Enhancer;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class CglibWeaver implements Weaver {
    private List<Aspect> aspects = new ArrayList<Aspect>();

    public void register(Class<? extends Annotation> annotation, Advice advice) {
        aspects.add(new SimpleAspect(new AnnotatedPointcut(annotation), advice));
    }

    @SuppressWarnings({"unchecked"})
    public <T> T weave(T object) {
        AspectApplyingMethodInterceptor interceptor = new AspectApplyingMethodInterceptor(object, arrayOfAspects());
        return (T) Enhancer.create(object.getClass(), interceptor);
    }

    private Aspect[] arrayOfAspects() {
        return aspects.toArray(new Aspect[aspects.size()]);
    }
}
