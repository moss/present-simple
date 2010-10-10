package net.m14m.presentsimple.pointcuts;

import net.m14m.presentsimple.Pointcut;

import java.lang.reflect.Method;

public class EveryMethod implements Pointcut {
    public boolean matches(Method method) {
        return true;
    }
}
