package ua.yelisieiev;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationEngine {
    public List<String> execRuns(Object someObject) throws InvocationTargetException, IllegalAccessException {
        List<String> results = new ArrayList<>();
        for (Method method : someObject.getClass().getMethods()) {
            if (method.getAnnotation(Run.class) != null && method.getParameterCount() == 0) {
                results.add(String.valueOf(method.invoke(someObject)));
            }
        }
        return results;
    }

    public void inject(Object someObject) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Field declaredField : someObject.getClass().getDeclaredFields()) {
            Inject injectAnnotation = declaredField.getAnnotation(Inject.class);
            if (injectAnnotation != null) {
//                System.out.println(declaredField.getName());
                declaredField.setAccessible(true);
                Class<?> injectClassType = injectAnnotation.classType();
                if (injectClassType == Void.class) {
                    declaredField.set(someObject,
                            declaredField.getType().getDeclaredConstructor().newInstance());
                } else {
                    declaredField.set(someObject,
                            injectClassType.getDeclaredConstructor().newInstance());
                }
            }
        }

    }
}
