package net.m14m.presentsimple.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class ProxiedMethodCallTest {
    ProxiedMethodCall invocation;

    @Before public void setUpInvocation() {
        SomeClass enhancedObject = (SomeClass) Enhancer.create(SomeClass.class, new MethodInterceptor() {
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                invocation = new ProxiedMethodCall(obj, method, args, proxy);
                return "Ignored return value from wrapper";
            }
        });
        enhancedObject.someMethod(3, "foo");
    }

    @Test public void invokeReturnsValue() throws Throwable {
        assertEquals("Number 3 and String foo", invocation.invoke());
    }

    @Test public void exposesInvocationDetails() throws NoSuchMethodException {
        assertThat("receiver", invocation.getReceiver(), instanceOf(SomeClass.class));
        assertEquals("method", SomeClass.class.getMethod("someMethod", int.class, String.class),
                invocation.getMethod());
        assertArrayEquals("arguments", new Object[]{3, "foo"}, invocation.getArguments());
    }

    @Test public void allowsChangingArguments() throws Throwable {
        invocation.getArguments()[0] = 5;
        invocation.getArguments()[1] = "bar";
        assertEquals("Number 5 and String bar", invocation.invoke());
    }

    public static class SomeClass {
        public String someMethod(int number, String string) {
            return String.format("Number %d and String %s", number, string);
        }
    }
}
