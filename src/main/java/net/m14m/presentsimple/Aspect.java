package net.m14m.presentsimple;

public interface Aspect {
    Pointcut getPointcut();

    Advice getAdvice();
}
