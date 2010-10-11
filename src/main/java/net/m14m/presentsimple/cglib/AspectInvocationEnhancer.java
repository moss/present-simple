package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Advice;
import net.m14m.presentsimple.Aspect;
import net.m14m.presentsimple.MethodInvocation;
import net.m14m.presentsimple.Pointcut;

public class AspectInvocationEnhancer {
    private Pointcut pointcut;
    private Advice advice;

    public AspectInvocationEnhancer(Aspect aspect) {
        pointcut = aspect.getPointcut();
        advice = aspect.getAdvice();
    }

    public MethodInvocation wrap(MethodInvocation invocation) {
        if (!pointcutMatches(invocation)) return invocation;
        return new AdviceDecoratedMethodInvocation(invocation, advice);
    }

    private boolean pointcutMatches(MethodInvocation invocation) {
        return pointcut.matches(invocation.getMethod());
    }
}
