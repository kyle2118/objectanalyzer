package com.test.domain;

import java.util.List;
import java.util.Map;

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

    public List<BeanB> getBeanBList() {
        return beanBList;
    }

    public void setBeanBList(List<BeanB> beanBList) {
        this.beanBList = beanBList;
    }

    public List<List<BeanB>> getBeanBListList() {
        return beanBListList;
    }

    public void setBeanBListList(List<List<BeanB>> beanBListList) {
        this.beanBListList = beanBListList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public Map<String, String> getStringStringMap() {
        return stringStringMap;
    }

    public void setStringStringMap(Map<String, String> stringStringMap) {
        this.stringStringMap = stringStringMap;
    }

    public Map<String, BeanB> getStringBeanBMap() {
        return stringBeanBMap;
    }

    public void setStringBeanBMap(Map<String, BeanB> stringBeanBMap) {
        this.stringBeanBMap = stringBeanBMap;
    }

    public Map<BeanB, String> getBeanBStringMap() {
        return beanBStringMap;
    }

    public void setBeanBStringMap(Map<BeanB, String> beanBStringMap) {
        this.beanBStringMap = beanBStringMap;
    }

    public Map<BeanB, BeanB> getBeanBBeanBMap() {
        return beanBBeanBMap;
    }

    public void setBeanBBeanBMap(Map<BeanB, BeanB> beanBBeanBMap) {
        this.beanBBeanBMap = beanBBeanBMap;
    }

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }

    public String getBeanAStringField() {
        return beanAStringField;
    }

    public void setBeanAStringField(String beanAStringField) {
        this.beanAStringField = beanAStringField;
    }
}
