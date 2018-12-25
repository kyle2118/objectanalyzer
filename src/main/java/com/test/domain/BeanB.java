package com.test.domain;

public class BeanB {

    private String beanBStringField;

    public BeanB(String beanBStringField) {
        this.beanBStringField = beanBStringField;
    }


    public BeanB(BeanB beanB) {
        this(beanB.getBeanBStringField());
    }

    public String getBeanBStringField() {
        return beanBStringField;
    }

    public void setBeanBStringField(String beanBStringField) {
        this.beanBStringField = beanBStringField;
    }
}
