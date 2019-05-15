package com.test.domain;

import com.test.api.Annotated;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BeanA {
    private List<BeanB> beanBList;
    private List<BeanC> beanCList;


    private BeanB beanB;
    //    private List<List<BeanB>> beanBListList;
    //    private List<String> stringList;
    //    private Map<String, String> stringStringMap;
    //    private Map<BeanB, String> beanBStringMap;
//    private Map<BeanB, BeanB> beanBBeanBMap;
    @Annotated
    private String beanAStringField1;
    private String beanAStringField2;

    private Map<String, BeanB> stringBeanBMap;
    private Map<String, List<BeanB>> stringListMap;
}
