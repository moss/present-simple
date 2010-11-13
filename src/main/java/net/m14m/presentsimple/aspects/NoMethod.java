package net.m14m.presentsimple.aspects;

import java.lang.reflect.Method;

public class NoMethod implements Pointcut {
    public boolean matches(Method method) {
        return false;
    }
}
