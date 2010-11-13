package net.m14m.presentsimple.aspects;

import java.lang.reflect.Method;

public class EveryMethod implements Pointcut {
    public boolean matches(Method method) {
        return true;
    }
}
