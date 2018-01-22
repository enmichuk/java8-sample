package com.enmichuk.lambda;

public class LambdaExample {
    public static void main(String[] args) {
        MathOperation addition = (int a, int b) -> a + b;
        System.out.println(addition.operation(1, 3));
    }

    interface MathOperation {
        int operation(int a, int b);
    }
}
