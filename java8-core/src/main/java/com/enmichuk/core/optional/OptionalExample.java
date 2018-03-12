package com.enmichuk.core.optional;

import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        Integer value1 = null;
        Integer value2 = 10;

        //Optional.ofNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.ofNullable(value1);

        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);
        System.out.println(a.map(c -> c + 1).orElse(0));
        System.out.println(sum(b, a));
    }

    static Optional<Integer> sum(Optional<Integer> value1, Optional<Integer> value2) {
        return value1.map(a -> a + value2.orElse(0));
    }
}
