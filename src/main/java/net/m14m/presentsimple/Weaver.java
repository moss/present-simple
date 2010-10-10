package net.m14m.presentsimple;

import net.m14m.presentsimple.cglib.CglibWeaver;

public interface Weaver {
    void register(Aspect aspect);

    <T> T weave(T object);

    public static class Factory {
        public static Weaver create(Aspect... aspects) {
            CglibWeaver weaver = new CglibWeaver();
            for (Aspect aspect : aspects) {
                weaver.register(aspect);
            }
            return weaver;
        }

        private Factory() {
        }
    }
}
