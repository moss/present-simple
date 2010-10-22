package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Advice;
import net.m14m.presentsimple.MethodInvocation;
import net.m14m.presentsimple.Pointcut;

public class Aspect {
    private Pointcut pointcut;
    private Advice advice;

    public Aspect(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    public MethodInvocation decorate(MethodInvocation invocation) {
        if (!pointcutMatches(invocation)) return invocation;
        return new AdviceDecoratedMethodInvocation(invocation, advice);
    }

    private boolean pointcutMatches(MethodInvocation invocation) {
        return pointcut.matches(invocation.getMethod());
    }
}