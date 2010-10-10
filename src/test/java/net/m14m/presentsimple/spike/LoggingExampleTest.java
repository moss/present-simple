package net.m14m.presentsimple.spike;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class LoggingExampleTest {
    @Test public void demo() {
        Weaver weaver = new Weaver(new Logging());
        LoggedClass obj = weaver.weaveAspectsInto(new LoggedClass());
        obj.loggedMethod();
        obj.unloggedMethod();
    }

    @Retention(RUNTIME)
    public @interface Logged {
    }

    public static class Logging implements Aspect {
        public Class<? extends Annotation> getAnnotation() {
            return Logged.class;
        }

        public Object intercept(MethodInvocation invocation) throws Throwable {
            System.out.println("Calling method " + invocation.getMethod().getName() + "...");
            Object result = invocation.invoke();
            System.out.println("...called method " + invocation.getMethod().getName() + ".");
            return result;
        }
    }

    public static class LoggedClass {
        @Logged
        public void loggedMethod() {
            System.out.println("Hi there!");
        }

        public void unloggedMethod() {
            System.out.println("Hi there in an unlogged way!");
        }
    }
}
