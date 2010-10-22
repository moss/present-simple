package net.m14m.presentsimple;

public interface Decorator {
    Object intercept(MethodCall call) throws Throwable;
}
