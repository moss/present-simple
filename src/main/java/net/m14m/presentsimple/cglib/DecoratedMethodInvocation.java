package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodInvocation;

import java.lang.reflect.Method;

public class DecoratedMethodInvocation implements MethodInvocation {
    private MethodInvocation invocation;
    private Decorator decorator;

    public DecoratedMethodInvocation(MethodInvocation invocation, Decorator decorator) {
        this.invocation = invocation;
        this.decorator = decorator;
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
        return decorator.advise(invocation);
    }
}
