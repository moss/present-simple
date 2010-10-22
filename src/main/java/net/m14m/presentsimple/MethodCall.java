package net.m14m.presentsimple;

import java.lang.reflect.Method;

public interface MethodCall {
    Object getReceiver();

    Method getMethod();

    Object[] getArguments();

    Object invoke() throws Throwable;
}
