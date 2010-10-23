* Handle nulls.
* Make sure it doesn't do anything for unannotated methods.
* After decorator.
* Around decorator.
* Reject running of method.
* Consider other kinds of decorator.
* What do we do if two Aspects offer the same Advice? Run it twice, or just once?
* Add JavaDoc.
* Package as jar.
* Distribute through Maven somehow?
* Expose the annotation to annotated aspects, so that it can add metadata?
* Do we really have to define a no-arg constructor for this to work? Must it be public?
* Any unit level tests needed for AdviceDecoratedMethodInvocation or AspectInvocationEnhancer? Better names?
* Profile and optimize?
* Python swallowing a coffee cup logo.
* (Not from code review but) maybe instead of making instances we should make classes? Then internal method calls
  could work correctly, and might fit in more happily with Pico, etc. Possibly harder to use, though?
