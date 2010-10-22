package net.m14m.presentsimple.advice;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;

import java.lang.reflect.Method;

public abstract class BeforeDecorator implements Decorator {
    public Object advise(MethodCall call) throws Throwable {
        before(call.getMethod(), call.getArguments(), call.getReceiver());
        return call.invoke();
    }

    public abstract void before(Method method, Object[] arguments, Object receiver);
}
