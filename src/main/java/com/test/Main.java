package com.test;

import com.test.api.Annotated;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Surgeon surgeon = new Surgeon();
        A a = new A("One", "Two", "Three");
        a.setBList(Arrays.asList(new B("B1 One", "B1 Two"), new B("B2 One", "B2 Two"), new B("B2 One", "B2 Two")));
        System.err.println(a);
        surgeon.ensure(a, true);
        System.err.println(a);
    }
}

@Data
class A {
    @Annotated
    private String line1;
    @Annotated
    private String line2;
    private String line3;
    List<B> bList;

    A(String line1, String line2, String line3) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
    }
}

class B {
    @Annotated
    private String line1;
    private String line2;

    B(String line1, String line2) {
        this.line1 = line1;
        this.line2 = line2;
    }
}
