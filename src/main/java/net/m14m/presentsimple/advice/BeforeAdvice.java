package net.m14m.presentsimple.advice;

import net.m14m.presentsimple.Advice;
import net.m14m.presentsimple.MethodInvocation;

import java.lang.reflect.Method;

public abstract class BeforeAdvice implements Advice {
    public Object advise(MethodInvocation invocation) throws Throwable {
        before(invocation.getMethod(), invocation.getArguments(), invocation.getReceiver());
        return invocation.invoke();
    }

    public abstract void before(Method method, Object[] arguments, Object receiver);
}
