package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.testing.samples.Logged;
import net.m14m.presentsimple.testing.samples.LoggingDecorator;
import net.m14m.presentsimple.testing.samples.SampleClass;
import net.m14m.presentsimple.testing.samples.TransactionalDecorator;
import org.junit.Before;
import org.junit.Test;

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
