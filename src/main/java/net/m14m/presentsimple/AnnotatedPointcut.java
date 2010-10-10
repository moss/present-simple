package net.m14m.presentsimple;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotatedPointcut implements Pointcut {
    private final Class<? extends Annotation> annotation;

    public AnnotatedPointcut(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    public boolean matches(Method method) {
        return method.isAnnotationPresent(annotation);
    }
}
