Right now we've got a working spike version. The code's a little hard to follow, and the test is way too
end-to-end-ish. Next up: build out a few individual pieces to do all the work that's happening in Weaver
right now:

* Pure AOP pieces:
  * Pointcut: something that decides whether an aspect applies to a given method.
    One implementation so far: AnnotationPointcut.
  * Advice: something that wraps a method invocation and adds extra behaviour to it.
    Implementations: AroundAdvice, BeforeAdvice, AfterAdvice.
  * MethodInvocation: what it says on the tin. Lets you see the method. Lets you see the arguments.
    Lets you see the method receiver. Lets you tamper with the arguments.
    Lets you go ahead with the invocation.
  * Aspect: a combination of a pointcut and advice.
  * Weaver: a place where aspects can be registered and then applied to objects.
    One implementation...
* CGLIB pieces:
  * CglibWeaver: a Weaver backed by CGLIB.
  * ProxiedMethodInvocation: a MethodInvocation implementation backed by a CGLIB MethodProxy.
  * AspectApplyingMethodInterceptor: a CGLIB Callback that applies aspects to a method when called.
  * PointcutCheckingCallbackFilter: a CGLIB CallbackFilter that rejects any methods not matching its
    collection of Pointcuts.

With that in mind, remaining tasks:

* AroundAdvice
* AnnotatedAroundAspect (worth doing? Maybe.)

* Handle nulls.
* Make sure it doesn't do anything for unannotated methods.
* After advice.
* Around advice.
* Reject running of method.
* Consider other kinds of advice.
* What do we do if two Aspects offer the same Advice? Run it twice, or just once?
* Add JavaDoc.
* Package as jar.
* Distribute through Maven somehow?
* Expose the annotation to annotated aspects, so that it can add metadata?
* Do we really have to define a no-arg constructor for this to work? Must it be public?
* Any unit level tests needed for AdviceDecoratedMethodInvocation or AspectInvocationEnhancer? Better names?
* Profile and optimize?

Notes from code review:

* Get away from AOP language: this is essentially Python-style decorators for Java. That's a much simpler way to
  present it.
* Ditch Aspect as a user-facing class--it could be purely internal, and absorbed into AspectInvocationEnhancer.
* Need to support anything but annotated pointcuts? Honestly, maybe not.
* Python swallowing a coffee cup logo.
* (Not from code review but) maybe instead of making instances we should make classes? Then internal method calls
  could work correctly, and might fit in more happily with Pico, etc. Possibly harder to use, though?
