package net.m14m.presentsimple;

import java.lang.reflect.Method;

public interface MethodInvocation {
    Object getReceiver();

    Method getMethod();

    Object[] getArguments();

    Object invoke() throws Throwable;
}
