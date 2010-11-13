package net.m14m.presentsimple.aspects;

import java.lang.reflect.Method;

public interface Pointcut {
    boolean matches(Method method);
}
