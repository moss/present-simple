package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.AppliesTo;
import net.m14m.presentsimple.Decorator;
import net.m14m.presentsimple.MethodCall;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.Assert.assertEquals;


public class CglibWeaverTest {
    private SampleClass instance;

    @Before public void setUpWeaver() {
        CglibWeaver weaver = new CglibWeaver();
        weaver.register(new LoggingDecorator());
        weaver.register(new TransactionalDecorator());
        instance = weaver.createInstance(SampleClass.class);
    }

    @Test public void shouldDecorateAnnotatedMethods() {
        assertEquals("Logged(contactServer())", instance.contactServer());
        assertEquals("Transactional(saveChanges())", instance.saveChanges());
    }

    @Test public void shouldAllowMultipleAnnotationsOnAMethod() {
        assertEquals("Transactional(Logged(clearDatabase()))", instance.clearDatabase());
    }

    @Test public void shouldNotAffectMethodsWithoutAnnotations() {
        assertEquals("sayHello()", instance.sayHello());
    }

    @Test public void shouldDecorateInternalMethodCalls() {
        assertEquals("Transactional(saveChanges()) Transactional(Logged(clearDatabase()))", instance.saveAndClear());
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Logged {
    }

    @AppliesTo(Logged.class)
    private static class LoggingDecorator implements Decorator {
        public Object intercept(MethodCall call) throws Throwable {
            return String.format("Logged(%s)", call.invoke());
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Transactional {
    }

    @AppliesTo(Transactional.class)
    private static class TransactionalDecorator implements Decorator {
        public Object intercept(MethodCall call) throws Throwable {
            return String.format("Transactional(%s)", call.invoke());
        }
    }

    public static class SampleClass {
        @Logged public String contactServer() { return "contactServer()"; }

        @Transactional public String saveChanges() { return "saveChanges()"; }

        @Logged @Transactional public String clearDatabase() { return "clearDatabase()"; }

        public String saveAndClear() {
            return saveChanges() + " " + clearDatabase();
        }

        public String sayHello() { return "sayHello()"; }
    }

}
