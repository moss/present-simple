package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.*;
import net.m14m.presentsimple.pointcuts.EveryMethod;
import net.m14m.presentsimple.pointcuts.NoMethod;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AspectApplyingMethodInterceptorTest {
    private List<String> log = new ArrayList<String>();
    private SomeClass objectToEnhance = new SomeClass(log);

    @Test public void adviceCanDoStuffBeforeAndAfterTheMethodInvocation() {
        SomeClass object = enhanceObject(new EveryMethod(), new LoggingAdvice());

        object.someMethod(7, "foo");

        assertEquals(Arrays.asList("before", "Number 7 and String foo", "after"), log);
    }

    @Test public void ignoresAdviceIfThePointcutDoesNotApply() {
        SomeClass object = enhanceObject(new NoMethod(), new LoggingAdvice());

        String result = object.someMethod(7, "foo");

        assertEquals(Arrays.asList("Number 7 and String foo"), log);
        assertEquals("Number 7 and String foo", result);
    }

    @Test @Ignore public void appliesMultipleAspects() {
        SomeClass object = enhanceObject(
                new SimpleAspect(new EveryMethod(), new LoggingAdvice("Log 1")),
                new SimpleAspect(new NoMethod(), new LoggingAdvice("Ignored Log")),
                new SimpleAspect(new EveryMethod(), new LoggingAdvice("Log 2"))
        );

        object.someMethod(7, "foo");

        assertEquals(Arrays.asList(
                "Log 1: before",
                "Log 2: before",
                "Number 7 and String foo",
                "Log 2: after",
                "Log 1: after"
        ), log);
    }

    private SomeClass enhanceObject(Pointcut pointcut, Advice advice) {
        Aspect aspect = new SimpleAspect(pointcut, advice);
        return enhanceObject(aspect);
    }

    private SomeClass enhanceObject(Aspect... aspects) {
        AspectApplyingMethodInterceptor interceptor = new AspectApplyingMethodInterceptor(objectToEnhance, aspects);
        return (SomeClass) Enhancer.create(SomeClass.class, interceptor);
    }

    public static class SomeClass {
        private List<String> log;

        public SomeClass() {}

        public SomeClass(List<String> log) {
            this.log = log;
        }

        public String someMethod(int number, String string) {
            String result = String.format("Number %d and String %s", number, string);
            log.add(result);
            return result;
        }
    }

    private class LoggingAdvice implements Advice {
        private String prefix;

        LoggingAdvice(String name) {
            this.prefix = name + ": ";
        }

        LoggingAdvice() {
            this.prefix = "";
        }

        public Object advise(MethodInvocation invocation) throws Throwable {
            log.add(prefix + "before");
            Object result = invocation.invoke();
            log.add(prefix + "after");
            return result;
        }
    }
}
