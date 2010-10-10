package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.*;
import net.m14m.presentsimple.pointcuts.EveryMethod;
import net.m14m.presentsimple.pointcuts.NoMethod;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AspectApplyingMethodInterceptorTest {
    private List<String> log = new ArrayList<String>();
    private SomeClass objectToEnhance = new SomeClass(log);

    @Test public void adviceCanDoStuffBeforeAndAfterTheMethodInvocation() {
        SomeClass object = enhanceObject(new EveryMethod(), new Advice() {
            public Object advise(MethodInvocation invocation) throws Throwable {
                log.add("before");
                Object result = invocation.invoke();
                log.add("after");
                return result;
            }
        });

        object.someMethod(7, "foo");

        assertEquals(Arrays.asList("before", "Number 7 and String foo", "after"), log);
    }

    @Test public void ignoresAdviceIfThePointcutDoesNotApply() {
        SomeClass object = enhanceObject(new NoMethod(), new Advice() {
            public Object advise(MethodInvocation invocation) throws Throwable {
                log.add("Oops! You listened to the advice!");
                return invocation.invoke();
            }
        });

        object.someMethod(7, "foo");

        assertEquals(Arrays.asList("Number 7 and String foo"), log);
    }

    private SomeClass enhanceObject(Pointcut pointcut, Advice advice) {
        Aspect aspect = new SimpleAspect(pointcut, advice);
        AspectApplyingMethodInterceptor interceptor = new AspectApplyingMethodInterceptor(objectToEnhance, aspect);
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

}
