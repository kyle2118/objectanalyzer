package com.test.domain;

import com.test.api.Annotated;
import lombok.Data;

@Data
public class BeanB {

    @Annotated
    private String beanBStringField1;
    private String beanBStringField2;

    public BeanB(String beanBStringField1) {
        this.beanBStringField1 = beanBStringField1;
    }


    public BeanB(BeanB beanB) {
        this(beanB.getBeanBStringField1());
    }
}
