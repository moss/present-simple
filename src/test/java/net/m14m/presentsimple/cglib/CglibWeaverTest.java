package net.m14m.presentsimple.cglib;

import net.m14m.presentsimple.Advice;
import net.m14m.presentsimple.MethodInvocation;
import net.m14m.presentsimple.SimpleAspect;
import net.m14m.presentsimple.pointcuts.AnnotatedPointcut;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.Assert.assertEquals;


public class CglibWeaverTest {
    private CglibWeaver weaver;

    @Before public void setUpWeaver() {
        weaver = new CglibWeaver();
        register(Logged.class, "Logged(%s)");
        register(Transactional.class, "Transactional(%s)");
    }

    @Test public void shouldWeaveAppropriateAspectsIntoClass() {
        SampleClass sample = weaver.weave(new SampleClass());
        assertEquals("Logged(contactServer())", sample.contactServer());
        assertEquals("Transactional(saveChanges())", sample.saveChanges());
        assertEquals("Transactional(Logged(clearDatabase()))", sample.clearDatabase());
        assertEquals("sayHello()", sample.sayHello());
    }

    private void register(Class<? extends Annotation> annotation, String format) {
        weaver.register(new SimpleAspect(new AnnotatedPointcut(annotation), new StringDecoratingAdvice(format)));
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Logged {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Transactional {
    }

    public static class SampleClass {
        @Logged public String contactServer() { return "contactServer()"; }

        @Transactional public String saveChanges() { return "saveChanges()"; }

        @Logged @Transactional public String clearDatabase() { return "clearDatabase()"; }

        public String sayHello() { return "sayHello()"; }
    }

    public static class BoringClass {
        public String greet() { return "Hello there."; }
    }

    private static class StringDecoratingAdvice implements Advice {
        private String format;

        public StringDecoratingAdvice(String format) {
            this.format = format;
        }

        public Object advise(MethodInvocation invocation) throws Throwable {
            return String.format(format, invocation.invoke());
        }
    }
}