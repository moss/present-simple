package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Aspect;
import net.m14m.presentsimple.Weaver;

public class CglibWeaver implements Weaver {
    public void register(Aspect aspect) {
    }

    public <T> T weave(T object) {
        return null;
    }
}
