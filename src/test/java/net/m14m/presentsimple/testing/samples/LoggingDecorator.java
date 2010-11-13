package net.m14m.presentsimple.testing.samples;

import net.m14m.presentsimple.AppliesTo;
import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;

@AppliesTo(Logged.class)
public class LoggingDecorator implements Decorator {
    public Object intercept(MethodCall call) throws Throwable {
        return String.format("Logged(%s)", call.invoke());
    }
}
