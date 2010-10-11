package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Aspect;
import net.m14m.presentsimple.Weaver;
import net.sf.cglib.proxy.Enhancer;

import java.util.ArrayList;
import java.util.List;

public class CglibWeaver implements Weaver {
    private List<Aspect> aspects = new ArrayList<Aspect>();

    public void register(Aspect aspect) {
        aspects.add(aspect);
    }

    @SuppressWarnings({"unchecked"})
    public <T> T weave(T object) {
        AspectApplyingMethodInterceptor interceptor = new AspectApplyingMethodInterceptor(object, arrayOfAspects());
        return (T) Enhancer.create(object.getClass(), interceptor);
    }

    private Aspect[] arrayOfAspects() {
        return aspects.toArray(new Aspect[aspects.size()]);
    }
}
