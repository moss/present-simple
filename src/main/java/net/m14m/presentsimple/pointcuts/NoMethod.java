package net.m14m.presentsimple.pointcuts;

import java.lang.reflect.Method;

public class NoMethod implements Pointcut {
    public boolean matches(Method method) {
        return false;
    }
}
