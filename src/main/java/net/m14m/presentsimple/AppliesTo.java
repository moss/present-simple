package net.m14m.presentsimple;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AppliesTo {
    public Class<? extends Annotation> value();
}
