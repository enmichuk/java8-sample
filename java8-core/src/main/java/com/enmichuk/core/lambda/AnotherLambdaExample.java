package com.enmichuk.core.lambda;

import java.util.function.BiFunction;

public class AnotherLambdaExample {
    private final static int finalVariable = 3;
    private static int nonFinalVariable = 3;

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> addition = (Integer a, Integer b) -> a + b;
        System.out.println(addition.apply(1, finalVariable));
        nonFinalVariable = 4;
        System.out.println(addition.apply(1, nonFinalVariable));
        int localVariable = 5;
        System.out.println(addition.apply(1, localVariable));
    }
}
