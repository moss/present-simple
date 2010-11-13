package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;
import net.m14m.presentsimple.aspects.AnnotatedPointcut;
import net.m14m.presentsimple.aspects.Aspect;
import net.m14m.presentsimple.testing.NamedMethodCall;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"UnusedDeclaration"})
public class AspectTest {
    private Aspect aspect;

    @Before public void setUp() throws Exception {
        aspect = new Aspect(new AnnotatedPointcut(Sample.class), new SimpleDecorator());
    }

    @Test public void matchesMethodsMatchingThePointcut() throws Throwable {
        assertTrue(aspect.pointcutMatches(new NamedMethodCall(this, "decorateMe")));
    }

    @Test public void doesNotMatchOtherMethods() throws Throwable {
        assertFalse(aspect.pointcutMatches(new NamedMethodCall(this, "doNotDecorateMe")));
    }

    @Sample public void decorateMe() {
    }

    public void doNotDecorateMe() {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface Sample {
    }

    private static class SimpleDecorator implements Decorator {
        public Object intercept(MethodCall call) throws Throwable {
            return null;
        }
    }
}
