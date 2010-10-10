package net.m14m.presentsimple.pointcuts;

import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnnotatedPointcutTest {
    private AnnotatedPointcut pointcut = new AnnotatedPointcut(AnAnnotation.class);

    @Test public void shouldMatchMethodsWithTheAnnotation() throws NoSuchMethodException {
        assertTrue(pointcut.matches(Sample.class.getMethod("annotated")));
    }

    @Test public void shouldNotMatchMethodsWithoutTheAnnotation() throws NoSuchMethodException {
        assertFalse(pointcut.matches(Sample.class.getMethod("notAnnotated")));
    }

    @Test(expected = Exception.class) public void
    shouldFailFastIfCreatedWithAnAnnotationThatIsNotRetainedAtRuntime() {
        new AnnotatedPointcut(UnretainedAnnotation.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnAnnotation {
    }

    public class Sample {
        @AnAnnotation public void annotated() {}

        public void notAnnotated() {}
    }

    public @interface UnretainedAnnotation {
    }
}
