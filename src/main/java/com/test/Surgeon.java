package com.test;


import com.test.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class Surgeon {
    private Logger logger;

    public Surgeon() {
        logger = new Logger();
    }

    public void ensure(Object object, boolean isEncrypt) {
        String command = isEncrypt ? "Encryption" : "Decryption";
        long startMs = System.currentTimeMillis();
        logger.log(String.format("%s of %s object starting.", command, object.getClass().getSimpleName()));

        try {
            // Analyze object itself

            // Handle Iterable
            Class<?> objectClass = object.getClass();

            if (Collection.class.isAssignableFrom(objectClass)) {
                handleCollection((Collection<?>) object, isEncrypt);

            }
            // Handle Map
            /*
                Map<String, string>
                Map<String, bean>
                Map<bean, String>
                Map<bean, bean>
             */
            if (Map.class.isAssignableFrom(objectClass)) {
                Map<?, ?> objectMap = (Map<?, ?>) object;
                handleMap((Map<?, ?>)object, isEncrypt);
                for (Map.Entry<?, ?> entry : objectMap.entrySet()) {
                    // entry key must be a primitive, wrapper or String
                    if (isComplex(entry.getValue().getClass())) {
                        ensure(entry.getValue(), isEncrypt);
                    }
                }
            }


            // Analyze guts of an object
            for (Field declaredField : object.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Type fieldType = declaredField.getType();
                /*System.out.println(declaredField.getClass());
                System.out.println((declaredField.getType()));
                System.out.println(declaredField.getDeclaringClass());
                System.out.println(fieldType.getTypeName());
                System.out.println(fieldType.getClass());*/

                if (Iterable.class.isAssignableFrom(declaredField.getType()) ||
                        Map.class.isAssignableFrom(declaredField.getType())) {
                    ensure(declaredField.get(object), isEncrypt);
                }
                /*if ((fieldType.getClass()).isAssignableFrom(Iterable.class) ||
                        fieldType.getClass().isAssignableFrom(Map.class)) {
                    ensure(declaredField.get(object), isEncrypt);
                }*/
            }
            // perform an operation

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.log(String.format("%s finished in", command));
        }

    }

    private void handleMap(Map<?, ?> map, boolean isEncrypt) {
        if (map == null) {
            return;
        }
        Class keyClass;
        Class valueClass;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            keyClass = entry.getKey().getClass();
            if (isComplex(keyClass)) {
                ensure(entry.getKey(), isEncrypt);
            }
            valueClass = entry.getValue().getClass();
            if (isComplex(valueClass)) {
                ensure(entry.getValue(), isEncrypt);
            }
        }
    }

    /**
     * Handles scenarios when a object has list of something.
     * Method gets the first element and iterates the rest
     * if type of the first is a user defined type, aka Bean
     *
     * @param collection is a collection(list, set, queue, stack, vector)
     */
    private void handleCollection(Collection<?> collection, boolean isEncrypt) {
        if (collection == null) {
            return;
        }
        Class typeClass;
        Iterator iterator = collection.iterator();
        if (iterator.hasNext()) {
            Object element = iterator.next();
            typeClass = element.getClass();
            logger.log(String.format("First element type: %s", typeClass));
            if (isComplex(typeClass)) {
                collection.forEach(e -> ensure(e, isEncrypt));
            }
        }


    }

    /**
     * Checks if a {@code clazz} is a user defined type, ie Bean
     *
     * @param clazz is a {@link Class} object
     * @return true if class is a bean, false otherwise
     */
    private boolean isComplex(Class<?> clazz) {

        // Number is a super type of Byte, Short, Integer, Float, Double
        return !Number.class.isAssignableFrom(clazz)
                || !Character.class.isAssignableFrom(clazz)
                || !CharSequence.class.isAssignableFrom(clazz);
    }
}
