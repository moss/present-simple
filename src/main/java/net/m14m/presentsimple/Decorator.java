package net.m14m.presentsimple;

public interface Decorator {
    Object advise(MethodInvocation invocation) throws Throwable;
}
