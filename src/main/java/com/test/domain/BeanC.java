package com.test.domain;

import com.test.api.Annotated;
import lombok.Data;

@Data
public class BeanC {
    @Annotated
    private String beanCStringField1;
    private String beanCStringField2;
    @Annotated
    private String beanCStringField3;
    @Annotated
    private String beanCStringField4;

    public BeanC(String beanCStringField1, String beanCStringField2, String beanCStringField3, String beanCStringField4) {
        this.beanCStringField1 = beanCStringField1;
        this.beanCStringField2 = beanCStringField2;
        this.beanCStringField3 = beanCStringField3;
        this.beanCStringField4 = beanCStringField4;
    }
}
