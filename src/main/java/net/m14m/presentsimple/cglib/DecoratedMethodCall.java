package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;

import java.lang.reflect.Method;

public class DecoratedMethodCall implements MethodCall {
    private MethodCall call;
    private Decorator decorator;

    public DecoratedMethodCall(MethodCall call, Decorator decorator) {
        this.call = call;
        this.decorator = decorator;
    }

    public Object getReceiver() {
        return call.getReceiver();
    }

    public Method getMethod() {
        return call.getMethod();
    }

    public Object[] getArguments() {
        return call.getArguments();
    }

    public Object invoke() throws Throwable {
        return decorator.advise(call);
    }
}
