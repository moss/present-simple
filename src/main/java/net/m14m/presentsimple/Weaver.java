package net.m14m.presentsimple;

public interface Weaver {
    void register(Decorator decorator);

    <T> T weave(T object);
}
