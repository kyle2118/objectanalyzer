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
                Collection<?> iterableObject = (Collection<?>) object;
                handleCollection((Collection<?>) object);
                // TODO refactor
                // Instead of iterating every element in case of a complex type
                // check once
                // If generic type is bean, then iterate and make a reflective call
                // Otherwise do not touch
                for (Object element : iterableObject) {
                    if (isComplex(element.getClass())) {
                        ensure(element, isEncrypt);
                    }
                }
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

    /**
     * Handles scenarios when a object has list of something.
     * Instead of iterating every element and checking its type,
     * method gets the first element and iterates the rest
     * if type is a user defined type, aka Bean
     *
     * @param collection is a collection(list, set, queue, stack, vector
     */
    private void handleCollection(Collection<?> collection) {
        Class typeClass = null;
        Iterator iterator = collection.iterator();
        if (iterator.hasNext()) {
            Object firstElement = iterator.next();
            typeClass = firstElement.getClass();
            logger.log(String.format("First element type: %s", typeClass));
            if (isComplex(typeClass)) {

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
