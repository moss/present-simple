package net.m14m.presentsimple;

public class SimpleAspect implements Aspect {
    private Pointcut pointcut;
    private Advice advice;

    public SimpleAspect(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }

    public Advice getAdvice() {
        return advice;
    }
}
