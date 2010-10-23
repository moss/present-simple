package net.m14m.presentsimple.decorators;

import net.m14m.presentsimple.testing.NamedMethodCall;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BeforeDecoratorTest {
    private List<String> log = new ArrayList<String>();

    @Test public void runsBeforeTheMethod() throws Throwable {
        BeforeDecorator advice = new BeforeDecorator() {
            public void before(Method method, Object[] arguments, Object receiver) {
                log.add("before");
            }
        };

        advice.intercept(new NamedMethodCall(this, "myMethod", 5));

        assertEquals("should have run interceptor and method", Arrays.asList("before", "number 5"), log);
    }

    @Test public void passesInInvocationDetails() throws Throwable {
        final Method expectedMethod = getClass().getMethod("myMethod", Integer.class);
        final Integer expectedArgument = 5;
        final Object expectedReceiver = this;

        BeforeDecorator advice = new BeforeDecorator() {
            public void before(Method method, Object[] arguments, Object receiver) {
                assertEquals("method", expectedMethod, method);
                assertArrayEquals("arguments", new Object[]{expectedArgument}, arguments);
                assertEquals("receiver", expectedReceiver, receiver);
            }
        };

        advice.intercept(new NamedMethodCall(this, "myMethod", expectedArgument));
    }

    public String myMethod(Integer number) {
        String result = "number " + number;
        log.add(result);
        return result;
    }

}
