package com.test;

import com.test.domain.BeanA;
import com.test.domain.BeanB;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class SurgeonTest {
    Surgeon surgeon = new Surgeon();


    private BeanA inputBean;

    @Before
    public void setup() {
        inputBean = new BeanA();
        BeanB beanB = new BeanB("beanBStringField");
        inputBean.setBeanB(beanB);
        inputBean.setBeanBList(Arrays.asList(new BeanB(beanB), new BeanB(beanB)));
    }

    @Test
    public void handleCollection() {
        surgeon.ensure(inputBean, true);
    }

}