package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;
import net.m14m.presentsimple.pointcuts.EveryMethod;
import net.m14m.presentsimple.pointcuts.NoMethod;
import net.m14m.presentsimple.pointcuts.Pointcut;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AspectApplyingMethodInterceptorTest {
    private List<String> log = new ArrayList<String>();

    @Test public void adviceCanDoStuffBeforeAndAfterTheMethodInvocation() throws Exception {
        SomeClass object = enhanceObject(new EveryMethod(), new LoggingDecorator());

        object.someMethod(7, "foo");

        assertEquals(Arrays.asList("before", "Number 7 and String foo", "after"), log);
    }

    @Test public void ignoresAdviceIfThePointcutDoesNotApply() throws Exception {
        SomeClass object = enhanceObject(new NoMethod(), new LoggingDecorator());

        String result = object.someMethod(7, "foo");

        assertEquals(Arrays.asList("Number 7 and String foo"), log);
        assertEquals("Number 7 and String foo", result);
    }

    @Test public void appliesMultipleAspects_FirstAddedIsClosestToTheActualInvocation() throws Exception {
        SomeClass object = enhanceObject(
                new Aspect(new EveryMethod(), new LoggingDecorator("Log 1")),
                new Aspect(new NoMethod(), new LoggingDecorator("Ignored Log")),
                new Aspect(new EveryMethod(), new LoggingDecorator("Log 2"))
        );

        object.someMethod(7, "foo");

        assertEquals(Arrays.asList(
                "Log 2: before",
                "Log 1: before",
                "Number 7 and String foo",
                "Log 1: after",
                "Log 2: after"
        ), log);
    }

    private SomeClass enhanceObject(Pointcut pointcut, Decorator decorator) throws Exception {
        return enhanceObject(new Aspect(pointcut, decorator));
    }

    private SomeClass enhanceObject(Aspect... aspects) throws Exception {
        AspectApplyingMethodInterceptor interceptor = new AspectApplyingMethodInterceptor(aspects);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SomeClass.class);
        enhancer.setCallbackType(AspectApplyingMethodInterceptor.class);
        Class enhancedClass = enhancer.createClass();
        Enhancer.registerCallbacks(enhancedClass, new Callback[]{interceptor});
        return (SomeClass) enhancedClass.getConstructor(List.class).newInstance(log);
    }

    public static class SomeClass {
        private List<String> log;

        public SomeClass(List<String> log) {
            this.log = log;
        }

        public String someMethod(int number, String string) {
            String result = String.format("Number %d and String %s", number, string);
            log.add(result);
            return result;
        }
    }

    private class LoggingDecorator implements Decorator {
        private String prefix;

        LoggingDecorator(String name) {
            this.prefix = name + ": ";
        }

        LoggingDecorator() {
            this.prefix = "";
        }

        public Object intercept(MethodCall call) throws Throwable {
            log.add(prefix + "before");
            Object result = call.invoke();
            log.add(prefix + "after");
            return result;
        }
    }
}
