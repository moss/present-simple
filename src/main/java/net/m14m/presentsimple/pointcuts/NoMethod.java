package net.m14m.presentsimple.pointcuts;

import net.m14m.presentsimple.Pointcut;

import java.lang.reflect.Method;

public class NoMethod implements Pointcut {
    public boolean matches(Method method) {
        return false;
    }
}
