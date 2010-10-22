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
    private CglibWeaver weaver;

    @Before public void setUpWeaver() {
        weaver = new CglibWeaver();
        weaver.register(new LoggingDecorator());
        weaver.register(new TransactionalDecorator());
    }

    @Test public void shouldDecorateAnnotatedMethods() {
        SampleClass sample = weaver.decorate(new SampleClass());
        assertEquals("Logged(contactServer())", sample.contactServer());
        assertEquals("Transactional(saveChanges())", sample.saveChanges());
        assertEquals("Transactional(Logged(clearDatabase()))", sample.clearDatabase());
        assertEquals("sayHello()", sample.sayHello());
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

        public String sayHello() { return "sayHello()"; }
    }

}
