package com.enmichuk.core.lambda;

public class LambdaExample {
    public static void main(String[] args) {
        MathOperation addition = (int a, int b) -> a + b;
        print(addition);
    }

    public static void print(MathOperation operation) {
        System.out.println(operation.operation(1, 3));
    }

    interface MathOperation {
        int operation(int a, int b);
    }
}
