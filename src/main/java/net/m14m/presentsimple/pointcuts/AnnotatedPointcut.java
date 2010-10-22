package net.m14m.presentsimple.pointcuts;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

public class AnnotatedPointcut implements Pointcut {
    private final Class<? extends Annotation> annotation;

    public AnnotatedPointcut(Class<? extends Annotation> annotation) {
        mustBeRetainedAtRuntime(annotation);
        this.annotation = annotation;
    }

    public boolean matches(Method method) {
        return method.isAnnotationPresent(annotation);
    }

    private void mustBeRetainedAtRuntime(Class<? extends Annotation> annotation) {
        if (retention(annotation) != RetentionPolicy.RUNTIME) {
            throw new IllegalArgumentException("Pointcut annotations must have runtime retention. " +
                    "Annotate with: @Retention(RetentionPolicy.RUNTIME)");
        }
    }

    private RetentionPolicy retention(Class<? extends Annotation> annotation) {
        if (!annotation.isAnnotationPresent(Retention.class)) return null;
        return annotation.getAnnotation(Retention.class).value();
    }
}
