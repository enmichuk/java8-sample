package com.enmichuk.core.methodreference;

import java.util.ArrayList;
import java.util.List;

public class StaticMethodReferenceExample {
    public static void main(String args[]) {
        List<String> names = new ArrayList<>();

        names.add("Mahesh");
        names.add("Suresh");
        names.add("Ramesh");
        names.add("Naresh");
        names.add("Kalpesh");

        names.forEach(System.out::println);
    }
}
