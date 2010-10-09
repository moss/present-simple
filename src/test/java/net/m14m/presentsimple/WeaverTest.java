package net.m14m.presentsimple;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WeaverTest {
    private boolean calledAdvice;
    private Weaver weaver;

    @Before public void setUp() throws Exception {
        weaver = new Weaver(new AnAspect());
    }

    @Test public void addsBeforeAdvice() {
        ObjectWithAspect decorated = weaver.weaveAspectsInto(new ObjectWithAspect());
        assertFalse("not executed yet", calledAdvice);
        decorated.decoratedMethod();
        assertTrue("advice should have been executed", calledAdvice);
        decorated.shouldHaveCalledDecoratedMethod();
    }

    @Test public void doesNotAddAdviceToMethodsWithoutAnnotation() {
        ObjectWithAspect decorated = weaver.weaveAspectsInto(new ObjectWithAspect());
        decorated.undecoratedMethod();
        assertFalse("no advice", calledAdvice);
        decorated.shouldHaveCalledUndecoratedMethod();
    }

    @Retention(RUNTIME)
    public @interface DecoratedWithAspect {
    }

    public class AnAspect implements Aspect {
        public Class<? extends Annotation> getAnnotation() {
            return DecoratedWithAspect.class;
        }

        public Object intercept(MethodInvocation invocation) throws Throwable {
            calledAdvice = true;
            return invocation.invoke();
        }
    }

    public static class ObjectWithAspect {
        private boolean calledDecoratedMethod;
        private boolean calledUndecoratedMethod;

        @DecoratedWithAspect
        public void decoratedMethod() {
            calledDecoratedMethod = true;
        }

        public void undecoratedMethod() {
            calledUndecoratedMethod = true;

        }

        public void shouldHaveCalledDecoratedMethod() {
            assertTrue("decorated method should have been called", calledDecoratedMethod);
        }

        public void shouldHaveCalledUndecoratedMethod() {
            assertTrue("undecorated method should have been called", calledUndecoratedMethod);
        }
    }
}
