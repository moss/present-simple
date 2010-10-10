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
* Sketch out interfaces a bit (is this premature? Dunno, it seems fair in this case.)
* AnnotatedPointcut
* AroundAdvice
* AnnotatedAroundAspect (worth doing? Maybe.)
* ProxiedMethodInvocation -- possibly hard test to set up
* AspectApplyingMethodInterceptor for one aspect -- possibly hard test to set up
* multiple aspects in AspectApplyingMethodInterceptor
* CglibWeaver
* Add PointcutCheckingCallbackFilter for efficiency
* Profile and optimize further (CallbackFilter seems like obvious low hanging fruit)

* Handle nulls.
* Make sure it doesn't do anything for unannotated methods.
* After advice.
* Around advice.
* Reject running of method.
* Consider other kinds of advice.
