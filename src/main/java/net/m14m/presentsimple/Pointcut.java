package net.m14m.presentsimple;

import java.lang.reflect.Method;

public interface Pointcut {
    boolean matches(Method method);
}
