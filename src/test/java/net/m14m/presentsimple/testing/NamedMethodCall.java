package net.m14m.presentsimple.testing;

import net.m14m.presentsimple.MethodCall;

import java.lang.reflect.Method;

public class NamedMethodCall implements MethodCall {
    private final Object receiver;
    private final Object[] arguments;
    private Method method;

    public NamedMethodCall(Object receiver, String methodName, Object... arguments) throws NoSuchMethodException {
        this.receiver = receiver;
        this.arguments = arguments;
        method = receiver.getClass().getMethod(methodName, argumentTypes());
    }

    private Class<?>[] argumentTypes() {
        Class<?>[] argumentTypes = new Class<?>[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argumentTypes[i] = arguments[i].getClass();
        }
        return argumentTypes;
    }

    public Object getReceiver() {
        return receiver;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Object invoke() throws Throwable {
        return method.invoke(receiver, arguments);
    }
}
