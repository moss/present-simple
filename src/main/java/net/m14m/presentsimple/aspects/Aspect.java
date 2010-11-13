package net.m14m.presentsimple.aspects;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;
import net.m14m.presentsimple.cglib.DecoratedMethodCall;

public class Aspect {
    private Pointcut pointcut;
    private Decorator decorator;

    public Aspect(Pointcut pointcut, Decorator decorator) {
        this.pointcut = pointcut;
        this.decorator = decorator;
    }

    public MethodCall decorate(MethodCall call) {
        if (!pointcutMatches(call)) return call;
        return new DecoratedMethodCall(call, decorator);
    }

    private boolean pointcutMatches(MethodCall call) {
        return pointcut.matches(call.getMethod());
    }
}
