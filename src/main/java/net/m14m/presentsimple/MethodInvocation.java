package net.m14m.presentsimple;

import java.lang.reflect.Method;

public interface MethodInvocation {
    Method getMethod();

    Object getReceiver();

    Object[] getArguments();

    Object invoke() throws Exception;
}
