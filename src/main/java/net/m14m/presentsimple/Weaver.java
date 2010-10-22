package net.m14m.presentsimple;

public interface Weaver {
    void register(Advice advice);

    <T> T weave(T object);
}
