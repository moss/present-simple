package net.m14m.presentsimple.testing.samples;

import net.m14m.presentsimple.AppliesTo;
import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;

@AppliesTo(Transactional.class)
public class TransactionalDecorator implements Decorator {
    public Object intercept(MethodCall call) throws Throwable {
        return String.format("Transactional(%s)", call.invoke());
    }
}
