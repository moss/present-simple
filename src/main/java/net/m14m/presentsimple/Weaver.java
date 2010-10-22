package net.m14m.presentsimple;

import java.lang.annotation.Annotation;

public interface Weaver {
    void register(Class<? extends Annotation> annotation, Advice advice);

    <T> T weave(T object);
}
