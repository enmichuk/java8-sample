package com.enmichuk.core.lambda;

public class LambdaWithClosureExample {
    private final static int final3 = 3;
    private static int nonFinal4 = 4;

    public static void main(String[] args) {
        MathOperation additionWith3 = a -> a + final3;
        System.out.println(additionWith3.operation(1));
        MathOperation additionWith4 = a -> a + nonFinal4;
        System.out.println(additionWith4.operation(1));
        int effectivelyFinal = 5;
        MathOperation additionWith5 = a -> a + effectivelyFinal;
        System.out.println(additionWith5.operation(1));
//        int nonEffectivelyFinal = 6;
//        nonEffectivelyFinal = 6;
//        MathOperation additionWith6 = a -> a + nonEffectivelyFinal;//CompilationError
//        System.out.println(additionWith6.operation(1));
    }

    interface MathOperation {
        int operation(int a);
    }
}
