package com.test.api;

import com.test.log.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Crypt {
    private Logger logger = new Logger();
    public enum Operation {
        ONE, TWO
    }
    public void crypt(Operation operation, Object object) {
        try {
            for (Field field: object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Annotation annotation = field.getAnnotation(Annotated.class);
                if (annotation != null && field.get(object) != null) {
                    // Just reverse a string
                    StringBuilder value = new StringBuilder((String) field.get(object));
                    value = value.reverse();
                    field.set(object, value.toString());
                }
            }
        } catch (Exception e) {
            logger.log(String.format("Exception while operation %s", operation.name()));
        }
    }

}
