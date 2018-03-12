package com.enmichuk.core.methodreference;

import java.util.function.Supplier;

public class InstanceMethodReferenceExample {
    public static void main(String[] args) {
        Car mercedes = new Car("Mercedes");
        Car bmw = new Car("BMW");
        print(mercedes::getName);
        print(bmw::getName);
    }

    public static void print(Supplier<String> carNameMethod) {
        System.out.println(carNameMethod.get());
    }
}
