package net.m14m.presentsimple.aspects;

import net.m14m.presentsimple.AppliesTo;
import net.m14m.presentsimple.Decorator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AspectRegistry {
    public List<Aspect> aspects = new ArrayList<Aspect>();

    public void register(Decorator decorator) {
        aspects.add(new Aspect(new AnnotatedPointcut(findAnnotation(decorator)), decorator));
    }

    public Aspect[] aspectArray() {
        return aspects.toArray(new Aspect[aspects.size()]);
    }

    private Class<? extends Annotation> findAnnotation(Decorator decorator) {
        return decorator.getClass().getAnnotation(AppliesTo.class).value();
    }
}