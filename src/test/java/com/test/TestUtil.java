package com.test;

import com.test.domain.BeanC;
import org.apache.commons.lang3.RandomStringUtils;

public class TestUtil {
    private static final int COUNT = 5;
    public static BeanC beanCGen() {
        String s1 = RandomStringUtils.random(COUNT);
        String s2 = RandomStringUtils.random(COUNT);
        String s3 = RandomStringUtils.random(COUNT);
        String s4 = RandomStringUtils.random(COUNT);
        return new BeanC(s1, s2, s3, s4);
    }
    public static String stringGen() {
        return RandomStringUtils.random(COUNT);
    }
}
