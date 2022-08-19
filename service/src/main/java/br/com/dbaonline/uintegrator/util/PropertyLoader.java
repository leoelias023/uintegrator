package br.com.dbaonline.uintegrator.util;

import lombok.NonNull;
import lombok.val;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class PropertyLoader {

    public static <T> T load(@NonNull Properties properties, @NonNull String prefix, Class<T> clazz) {
        val instance = newInstance(clazz);
        val propsIterator = properties.keys().asIterator();

        while(propsIterator.hasNext()) {
            val property = propsIterator.next().toString();
            setFieldIfExists(instance, property.substring(property.indexOf(prefix) + prefix.length() + 1), properties.getProperty(property));
        }

        return instance;
    }

    private static void setFieldIfExists(@NonNull Object object, @NonNull String fieldName, @NonNull Object value) {

        val clazz = object.getClass();

        if (clazz == null) {
            return;
        }

        try {
            val field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    }

    private static <T> T newInstance(Class<T> clazz) {
        try {
            return getConstructor(clazz).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Could not possible create new instance of " + clazz.getName(), e);
        }
    }

    private static <T> Constructor<T> getConstructor(Class<T> clazz) {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Could not load property, No args constructor cannot be found.", e);
        }
    }
}
