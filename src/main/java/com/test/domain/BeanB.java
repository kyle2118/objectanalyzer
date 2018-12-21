package com.test.domain;

import lombok.Data;

@Data
public class BeanB {
    private String beanBStringField;

    public BeanB(String beanBStringField) {
        this.beanBStringField = beanBStringField;
    }
}
