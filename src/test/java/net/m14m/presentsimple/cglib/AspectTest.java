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

import static org.junit.Assert.assertEquals;

public class AspectTest {
    private static final String DECORATED_RESULT = "decorated";
    private static final String UNDECORATED_RESULT = "not decorated";
    private Aspect aspect;

    @Before public void setUp() throws Exception {
        aspect = new Aspect(new AnnotatedPointcut(Sample.class), new SimpleDecorator());
    }

    @Test public void decoratesMethodsMatchingThePointcut() throws Throwable {
        MethodCall call = aspect.decorate(new NamedMethodCall(this, "decorateMe"));
        assertEquals(DECORATED_RESULT, call.invoke());
    }

    @Test public void doesNotDecorateOtherMethods() throws Throwable {
        MethodCall call = aspect.decorate(new NamedMethodCall(this, "doNotDecorateMe"));
        assertEquals(UNDECORATED_RESULT, call.invoke());
    }

    @Sample public String decorateMe() {
        return UNDECORATED_RESULT;
    }

    public String doNotDecorateMe() {
        return UNDECORATED_RESULT;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface Sample {
    }

    private static class SimpleDecorator implements Decorator {
        public Object intercept(MethodCall call) throws Throwable {
            return DECORATED_RESULT;
        }
    }
}
