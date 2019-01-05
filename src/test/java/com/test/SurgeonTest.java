package com.test;

import com.test.domain.BeanA;
import com.test.domain.BeanB;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SurgeonTest {
    Surgeon surgeon = new Surgeon();


    private BeanA inputBean;

    @Before
    public void setup() {
        inputBean = new BeanA();
        BeanB beanB1 = new BeanB("123");
        BeanB beanB2 = new BeanB("456");
        BeanB beanB3 = new BeanB("789");
//        inputBean.setBeanB(beanB);
//        inputBean.setBeanBList(Arrays.asList(new BeanB(beanB), new BeanB(beanB)));
//        inputBean.setBeanAStringField1("01234");
        Map<String, BeanB> map = new HashMap<>();
        map.put("Key1", beanB1);
        map.put("Key2", beanB2);
        map.put("Key3", beanB3);
        inputBean.setStringBeanBMap(map);
    }

    @Test
    public void handleCollection() {
        System.out.println(inputBean);
        surgeon.doIt(inputBean, true);
        System.out.println(inputBean);

    }

}