package net.m14m.presentsimple;

import java.lang.reflect.Method;

public interface MethodInvocation {
    public Object invoke() throws Throwable;

    Method getMethod();
}
