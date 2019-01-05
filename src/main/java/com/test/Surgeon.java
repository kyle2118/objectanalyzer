package com.test;


import com.test.api.Crypt;
import com.test.domain.BeanA;
import com.test.domain.BeanB;
import com.test.log.Logger;

import javax.lang.model.type.PrimitiveType;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class Surgeon {
    public Logger logger;
    public Crypt crypt;

    public Surgeon() {
        logger = new Logger();
        crypt = new Crypt();
    }

    public void method1(Object object, boolean isEncrypt) {
//        String command = isEncrypt ? "Encryption" : "Decryption";
        long startNs = System.nanoTime();
//        logger.log(String.format("%s of %s object starting.", command, object.getClass().getSimpleName()));
        try {
            ensure(object, isEncrypt);
        } catch (Exception e) {
            logger.log("Exception!!!");
            throw e;
        } finally {
//            logger.log(String.format("%s finished in %d ms", command, (System.nanoTime() - startNs) / 1000));
        }
    }

    public void method2(Object object, boolean isEncrypt) {
        try {

            if (object instanceof BeanA) {
                BeanA beanA = (BeanA) object;
                Crypt.Operation operation = isEncrypt? Crypt.Operation.ONE: Crypt.Operation.TWO;
                crypt.crypt(operation, beanA);
                crypt.crypt(operation, beanA.getBeanB());
                for (Map.Entry<String, BeanB> entry: beanA.getStringBeanBMap().entrySet()) {
                    crypt.crypt(operation, entry.getValue());
                }
            }
        } catch (Exception e) {
            logger.log("Exception!!!");
            throw e;
        }
    }

    public void ensure(Object object, boolean isEncrypt) {
        if (object == null) {
            return;
        }
        try {
            // Analyze object itself
            Class<?> objectClass = object.getClass();

            // Handle collection
            if (Collection.class.isAssignableFrom(objectClass)) {
                handleCollection((Collection<?>) object, isEncrypt);
                return;
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
//                handleMap((Map<?, ?>)object, isEncrypt);
                for (Map.Entry<?, ?> entry : objectMap.entrySet()) {
                    // entry key must be a primitive, wrapper or String
                    if (isComplex(entry.getValue().getClass())) {
                        ensure(entry.getValue(), isEncrypt);
                    }
                }
                return;
            }


            // Analyze guts of an object
            for (Field declaredField : object.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);

                Class<?> fieldType = declaredField.getType();

                // reflect a map, collection or a bean
                if (Collection.class.isAssignableFrom(fieldType)
                        || Map.class.isAssignableFrom(fieldType)
                        || isComplex(fieldType)) {
                    ensure(declaredField.get(object), isEncrypt);
                }
            }
            // perform an operation
            Crypt.Operation operation = isEncrypt? Crypt.Operation.ONE: Crypt.Operation.TWO;
            crypt.crypt(operation, object);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleMap(Map<?, ?> map, boolean isEncrypt) {
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
    public void handleCollection(Collection<?> collection, boolean isEncrypt) {
        if (collection == null) {
            return;
        }
        for (Object o : collection) {
            Class<?> elementClass = o.getClass();
            if (!Collection.class.isAssignableFrom(elementClass)
                    || !Map.class.isAssignableFrom(elementClass)
                    || !isComplex(elementClass)) {
                break;
            }
            ensure(o, isEncrypt);
        }
        /*Class typeClass;
        Iterator iterator = collection.iterator();
        if (iterator.hasNext()) {
            Object element = iterator.next();
            typeClass = element.getClass();
            logger.log(String.format("First element type: %s", typeClass));
            if (isComplex(typeClass)) {
                for (Object o : collection) {
                    ensure(o, isEncrypt);
                }
            }
        }*/


    }

    /**
     * Checks if a {@code clazz} is a user defined type, ie Bean
     *
     * @param clazz is a {@link Class} object
     * @return true if class is a bean, false otherwise
     */
    public boolean isComplex(Class<?> clazz) {

        // Number is a super type of Byte, Short, Integer, Float, Double

        return !PrimitiveType.class.isAssignableFrom(clazz)
                && !Number.class.isAssignableFrom(clazz)
                && !Character.class.isAssignableFrom(clazz)
                && !CharSequence.class.isAssignableFrom(clazz);
    }
}
