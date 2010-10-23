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
        SampleClass instance = weaver.createInstance(SampleClass.class);
        assertEquals("Logged(contactServer())", instance.contactServer());
        assertEquals("Transactional(saveChanges())", instance.saveChanges());
    }

    @Test public void shouldAllowMultipleAnnotationsOnAMethod() {
        SampleClass instance = weaver.createInstance(SampleClass.class);
        assertEquals("Transactional(Logged(clearDatabase()))", instance.clearDatabase());
    }

    @Test public void shouldNotAffectMethodsWithoutAnnotations() {
        SampleClass instance = weaver.createInstance(SampleClass.class);
        assertEquals("sayHello()", instance.sayHello());
    }

    @Test public void shouldDecorateInternalMethodCalls() {
        SampleClass instance = weaver.createInstance(SampleClass.class);
        assertEquals("Transactional(saveChanges()) Transactional(Logged(clearDatabase()))", instance.saveAndClear());
    }

    @Test public void shouldCreateClassWithConstructorMatchingSuper() throws Exception {
        Class<? extends NoDefaultConstructor> decoratedClass = weaver.decorateClass(NoDefaultConstructor.class);
        NoDefaultConstructor instance = decoratedClass.getConstructor(String.class).newInstance("foo");
        assertEquals("Logged(constructor parameter: foo)", instance.showConstructorParameter());
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

    public static class NoDefaultConstructor {
        private String constructorParameter;

        public NoDefaultConstructor(String constructorParameter) {
            this.constructorParameter = constructorParameter;
        }

        @Logged public String showConstructorParameter() {
            return "constructor parameter: " + constructorParameter;
        }
    }
}
