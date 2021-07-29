package ua.yelisieiev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotationsTest {
    @DisplayName("Runs @Run-annotated methons of a given object")
    @Test
    public void test_runRunRun() throws InvocationTargetException, IllegalAccessException {
        AnnotationEngine engine = new AnnotationEngine();
        List<String> runResults = engine.execRuns(new TestClass());
        assertTrue(runResults.remove("Hello"));
        assertTrue(runResults.remove("3.14"));
        assertEquals(0, runResults.size());
    }

    @DisplayName("Assigns new class of annotation parameter type to @Inject()-annotated fields given object")
    @Test
    public void test_injectObjectAnnotated() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AnnotationEngine engine = new AnnotationEngine();
        TestClass testObject = new TestClass();
        engine.inject(testObject);
        assertEquals(String.class, testObject.name.getClass());
        assertNotNull(testObject.name);
        assertEquals(ArrayList.class, testObject.cars.getClass());
        assertNotNull(testObject.cars);
    }

    @DisplayName("Assigns new class of field type to @Inject(Void)-annotated fields given object")
    @Test
    public void test_injectObjectByField() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AnnotationEngine engine = new AnnotationEngine();
        TestClass testObject = new TestClass();
        engine.inject(testObject);
        assertNotNull(testObject.siblings);

    }

    public static class TestClass {
        @Inject(classType = String.class)
        Object name;
        Integer age = 16;
        @Inject(classType = ArrayList.class)
        Object cars;
        @Inject(classType = Void.class)
        ArrayList<Object> siblings;
        boolean hasDriverLicence = false;
        Boolean beenToKorea = true;

        @Run
        public String sayHello() {
            return "Hello";
        }

        @Run
        public int getSum(int a, int b) {
            return a + b;
        }

        @Run
        public double getPi() {
            return 3.14;
        }
    }

    public static void main(String[] args) {
//        Double d = new Double(11.0)
//        Integer i = new Integer(1);
//        d = (doublt)0 + i;
    }
}
