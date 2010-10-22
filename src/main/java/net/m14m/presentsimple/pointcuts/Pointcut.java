package net.m14m.presentsimple.pointcuts;

import java.lang.reflect.Method;

public interface Pointcut {
    boolean matches(Method method);
}
