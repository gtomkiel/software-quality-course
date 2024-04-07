package com.jabberpoint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

public class TestUtils {
    public static String getTestImagePath() {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        URL url = classLoader.getResource("test.png");
        assert url != null;
        return url.getFile();
    }

    public static <T> T getPrivateField(Object object, String fieldName, Class<T> fieldType)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return fieldType.cast(field.get(object));
    }

    public static Method getPrivateMethod(Object object, String methodName, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        Method method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method;
    }
}
