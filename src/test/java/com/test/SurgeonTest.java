package com.test;

import com.test.domain.BeanA;
import com.test.domain.BeanB;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurgeonTest {
    private static final long TEST_NUMBER = 100;
    private static final long MAP_SIZE = 100_000;
    private static final long LIST_SIZE = 100_000;

    private Surgeon surgeon = new Surgeon();


    private BeanA inputBean;


    @Before
    public void setup() {
        inputBean = new BeanA();
        inputBean.setBeanB(new BeanB("abcdef"));
//        inputBean.setBeanBList(Arrays.asList(new BeanB(beanB), new BeanB(beanB)));
        inputBean.setBeanAStringField1("01234");
        Map<String, BeanB> map = new HashMap<>();
        List<BeanB> list = new ArrayList<>();
        for (int i = 0; i < MAP_SIZE; i++) {
            map.put("Key" + i, new BeanB(i + "" + (i + 1)));
        }
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(new BeanB(i + "" + (i + 1)));
        }
        inputBean.setStringBeanBMap(map);
        inputBean.setBeanBList(list);
    }

    /**
     * Using reflection
     */
    @Test
    public void checkPerfomance() {

//        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(inputBean));
        System.out.println("Reflection:");
        long startNs = System.nanoTime();
        for (int i = 0; i < TEST_NUMBER; i++) {
            surgeon.method1(inputBean, true);
        }
        System.out.println("Finished reflection: " + ((System.nanoTime() - startNs) / 1_000_000) + " ms");
//        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(inputBean));
    }

    /**
     * Not using reflection
     */
    @Test
    public void approach2() {
        System.out.println("Standart:");
        long startNs = System.nanoTime();
        for (int i = 0; i < TEST_NUMBER; i++) {
            surgeon.method2(inputBean, true);
        }
        System.out.println("Finished standard: " + ((System.nanoTime() - startNs) / 1_000_000) + " ms");
    }
//    @Test
    public void handleCollectionDirectly() {
        List<BeanB> list = new ArrayList<>();
        list.add(new BeanB("someString"));
        surgeon.handleCollection(list, true);
    }
//    @Test
    public void handleCollection() {
        long startNs = System.nanoTime();
        BeanA beanA = new BeanA();
        List<BeanB> list = new ArrayList<>();
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(new BeanB("someString" + i));
        }
        beanA.setBeanBList(list);
        boolean operation = true;
        for (int i = 0; i < TEST_NUMBER; i++) {
            surgeon.ensure(beanA, operation);
            operation = !operation;
        }
        System.out.println("Finished in " + ((System.nanoTime() - startNs) / 1_000_000));
    }


}