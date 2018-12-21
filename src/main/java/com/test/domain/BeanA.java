package com.test.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BeanA {
    private List<BeanB> beanBList;
    private List<List<BeanB>> beanBListList;
    private List<String> stringList;
    private Map<String, String> stringStringMap;
    private Map<String, BeanB> stringBeanBMap;
    private Map<BeanB, String> beanBStringMap;
    private Map<BeanB, BeanB> beanBBeanBMap;
    private BeanB beanB;
    private String beanAStringField;
}
