package net.m14m.presentsimple.spike;

import java.lang.reflect.Method;

public interface MethodInvocation {
    public Object invoke() throws Throwable;

    Method getMethod();
}
