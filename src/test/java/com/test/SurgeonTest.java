package com.test;

import com.test.domain.BeanA;
import com.test.domain.BeanB;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.TestUtil.beanCGen;
import static com.test.TestUtil.stringGen;

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

    @Test
    public void surgeonTest() {
        int times = 50_000;

        long mean1 = 0;
        for (int i = 0; i < times; i++) {
            long startNs = System.nanoTime();
            surgeon.method2(beanAGen(), true);
            long finish = System.nanoTime();
            mean1 += (finish - startNs);
        }
        System.err.println(mean1 / (times * 1.0));
        long mean = 0;
        for (int i = 0; i < times; i++) {
            long startNs = System.nanoTime();
            surgeon.method1(beanAGen(), true);
            long finish = System.nanoTime();
            mean += (finish - startNs);
        }
        System.err.println(mean / (times * 1.0));
    }

    private BeanA beanAGen() {
        BeanB beanB1 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
        BeanB beanB2 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
        BeanB beanB3 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
        BeanB beanB4 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));

        BeanA beanA = new BeanA();
        beanA.setBeanBList(Arrays.asList(beanB1, beanB2, beanB3, beanB4));
        beanA.setBeanCList(Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen(), beanCGen()));
        beanA.setBeanB(new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen())));
        beanA.setBeanAStringField1(stringGen());
        beanA.setBeanAStringField2(stringGen());

        Map<String, BeanB> stringBeanBMap = new HashMap<>() {{
            put(stringGen(), new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen())));
            put(stringGen(), new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen())));
            put(stringGen(), new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen())));
        }};
        beanA.setStringBeanBMap(stringBeanBMap);

        Map<String, List<BeanB>> stringListMap = new HashMap<>() {{
            BeanB b1 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
            BeanB b2 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
            BeanB b3 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
            BeanB b4 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
            BeanB b5 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
            BeanB b6 = new BeanB(stringGen(), stringGen(), Arrays.asList(beanCGen(), beanCGen(), beanCGen(), beanCGen()));
            put(stringGen(), Arrays.asList(b1, b2, b3));
            put(stringGen(), Arrays.asList(b4, b5, b6));
        }};
        beanA.setStringListMap(stringListMap);
        return beanA;
    }

    /**
     * Using reflection
     */
    @Test
    public void checkPerformance() {

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
        System.out.println("Standard:");
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