package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodInvocation;
import net.m14m.presentsimple.pointcuts.Pointcut;

public class Aspect {
    private Pointcut pointcut;
    private Decorator decorator;

    public Aspect(Pointcut pointcut, Decorator decorator) {
        this.pointcut = pointcut;
        this.decorator = decorator;
    }

    public MethodInvocation decorate(MethodInvocation invocation) {
        if (!pointcutMatches(invocation)) return invocation;
        return new DecoratedMethodInvocation(invocation, decorator);
    }

    private boolean pointcutMatches(MethodInvocation invocation) {
        return pointcut.matches(invocation.getMethod());
    }
}
