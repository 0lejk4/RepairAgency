package com.gelo.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The enum Bean storage.
 * This class stores Instances of Classes aka Beans.
 */
public enum BeanStorage {
    /**
     * Instance bean storage.
     */
    INSTANCE;

    /**
     * The Objects.
     */
    Map<String, Object> objects = new ConcurrentHashMap<>();

    /**
     * Add.
     *
     * @param name   the name
     * @param object the object
     */
    public void add(String name, Object object) {
        objects.put(name, object);
    }

    /**
     * Add.
     *
     * @param <T>    the type parameter
     * @param clazz  the clazz
     * @param object the object
     */
    public <T> void add(Class<T> clazz, T object) {
        add(clazz.getName(), object);
    }

    /**
     * Get object.
     *
     * @param name the name
     * @return the object
     */
    public Object get(String name) {
        return objects.get(name);
    }

    /**
     * Get t.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    public <T> T get(Class<T> clazz) {
        return (T) get(clazz.getName());
    }
}
