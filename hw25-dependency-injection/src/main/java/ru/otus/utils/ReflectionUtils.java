package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((Method method) -> method.isAnnotationPresent(annotation))
                .toList();
    }
    public static <T> T newInstance(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Object invokeMethod(Object component, Method method, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(component, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private ReflectionUtils() {}
}
