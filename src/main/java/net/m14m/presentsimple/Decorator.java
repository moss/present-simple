package net.m14m.presentsimple;

public interface Decorator {
    Object advise(MethodCall call) throws Throwable;
}
