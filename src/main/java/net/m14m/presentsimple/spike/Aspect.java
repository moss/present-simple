package net.m14m.presentsimple.spike;

import java.lang.annotation.Annotation;

public interface Aspect {
    Class<? extends Annotation> getAnnotation();

    Object intercept(MethodInvocation invocation) throws Throwable;
}
