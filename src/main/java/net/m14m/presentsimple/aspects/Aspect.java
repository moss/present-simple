package net.m14m.presentsimple.aspects;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;

public class Aspect {
    private Pointcut pointcut;
    private Decorator decorator;

    public Aspect(Pointcut pointcut, Decorator decorator) {
        this.pointcut = pointcut;
        this.decorator = decorator;
    }

    public boolean pointcutMatches(MethodCall call) {
        return pointcut.matches(call.getMethod());
    }

    public Decorator getDecorator() {
        return decorator;
    }
}
