package net.m14m.presentsimple.pointcuts;

import java.lang.reflect.Method;

public class EveryMethod implements Pointcut {
    public boolean matches(Method method) {
        return true;
    }
}
