package net.m14m.presentsimple.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class ProxiedMethodInvocationTest {
    ProxiedMethodInvocation invocation;

    @Before public void setUpInvocation() {
        SomeClass wrapper = (SomeClass) Enhancer.create(SomeClass.class, new MethodInterceptor() {
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                invocation = new ProxiedMethodInvocation(obj, method, args, proxy);
                return "Ignored return value from wrapper";
            }
        });
        wrapper.someMethod(3, "foo");
    }

    @Test public void invokeShouldRunMethodAndReturnValue() throws Throwable {
        assertEquals("Number 3 and String foo", invocation.invoke());
    }

    public static class SomeClass {
        public String someMethod(int number, String string) {
            return String.format("Number %d and String %s", number, string);
        }
    }
}
