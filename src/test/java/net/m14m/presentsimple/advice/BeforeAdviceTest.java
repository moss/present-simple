package net.m14m.presentsimple.advice;

import net.m14m.presentsimple.MethodInvocation;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BeforeAdviceTest {
    private List<String> log = new ArrayList<String>();

    @Test public void runsBeforeTheMethod() throws Throwable {
        BeforeAdvice advice = new BeforeAdvice() {
            public void before(Method method, Object[] arguments, Object receiver) {
                log.add("before");
            }
        };

        advice.advise(new NamedMethodInvocation(this, "myMethod", 5));

        assertEquals(Arrays.asList("before", "number 5"), log);
    }

    public String myMethod(Integer number) {
        String result = "number " + number;
        log.add(result);
        return result;
    }

    public static class NamedMethodInvocation implements MethodInvocation {
        private final Object receiver;
        private final Object[] arguments;
        private Method method;

        public NamedMethodInvocation(Object receiver, String methodName, Object... arguments) throws NoSuchMethodException {
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
