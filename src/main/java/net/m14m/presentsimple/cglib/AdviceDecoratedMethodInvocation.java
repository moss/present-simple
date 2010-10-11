package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Advice;
import net.m14m.presentsimple.MethodInvocation;

import java.lang.reflect.Method;

public class AdviceDecoratedMethodInvocation implements MethodInvocation {
    private MethodInvocation invocation;
    private Advice advice;

    public AdviceDecoratedMethodInvocation(MethodInvocation invocation, Advice advice) {
        this.invocation = invocation;
        this.advice = advice;
    }

    public Object getReceiver() {
        return invocation.getReceiver();
    }

    public Method getMethod() {
        return invocation.getMethod();
    }

    public Object[] getArguments() {
        return invocation.getArguments();
    }

    public Object invoke() throws Throwable {
        return advice.advise(invocation);
    }
}
