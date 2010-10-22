package net.m14m.presentsimple.advice;

import net.m14m.presentsimple.MethodCall;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BeforeAdviceTest {
    private List<String> log = new ArrayList<String>();

    @Test public void runsBeforeTheMethod() throws Throwable {
        BeforeDecorator advice = new BeforeDecorator() {
            public void before(Method method, Object[] arguments, Object receiver) {
                log.add("before");
            }
        };

        advice.advise(new NamedMethodCall(this, "myMethod", 5));

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

        advice.advise(new NamedMethodCall(this, "myMethod", expectedArgument));
    }

    public String myMethod(Integer number) {
        String result = "number " + number;
        log.add(result);
        return result;
    }

    public static class NamedMethodCall implements MethodCall {
        private final Object receiver;
        private final Object[] arguments;
        private Method method;

        public NamedMethodCall(Object receiver, String methodName, Object... arguments) throws NoSuchMethodException {
            this.receiver = receiver;
            this.arguments = arguments;
            method = receiver.getClass().getMethod(methodName, argumentTypes());
        }

        private Class<?>[] argumentTypes() {
            Class<?>[] argumentTypes = new Class<?>[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                argumentTypes[i] = arguments[i].getClass();
            }
            return argumentTypes;
        }

        public Object getReceiver() {
            return receiver;
        }

        public Method getMethod() {
            return method;
        }

        public Object[] getArguments() {
            return arguments;
        }

        public Object invoke() throws Throwable {
            return method.invoke(receiver, arguments);
        }
    }
}
