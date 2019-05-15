package com.test.domain;

import com.test.api.Annotated;
import lombok.Data;

import java.util.List;

@Data
public class BeanB {

    @Annotated
    private String beanBStringField1;
    private String beanBStringField2;

    private List<BeanC> beanCList;

    public BeanB() {
    }

    public BeanB(String beanBStringField1) {
        this.beanBStringField1 = beanBStringField1;
    }

    public BeanB(String beanBStringField1, String beanBStringField2, List<BeanC> beanCList) {
        this.beanBStringField1 = beanBStringField1;
        this.beanBStringField2 = beanBStringField2;
        this.beanCList = beanCList;
    }
}
