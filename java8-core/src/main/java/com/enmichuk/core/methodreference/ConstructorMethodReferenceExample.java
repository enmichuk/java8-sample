package com.enmichuk.core.methodreference;

import java.util.function.Function;
import java.util.function.Supplier;

public class ConstructorMethodReferenceExample {
    public static void main(String[] args) {
        Car car1 = createCar(Car::new);
        System.out.println(car1.getName());
        Car car2 = createCar(() -> new Car());
        System.out.println(car2.getName());
        Car car3 = createCar(new Supplier() {
            @Override
            public Car get() {
                return new Car();
            }
        });
        System.out.println(car3.getName());

        Car car4 = createCar(Car::new, "Mercedes");
        System.out.println(car4.getName());
        Car car5 = createCar(s -> new Car(s), "Volvo");
        System.out.println(car5.getName());
        Car car6 = createCar(new Function<String, Car>() {
            @Override
            public Car apply(String s) {
                return new Car(s);
            }
        }, "BMW");
        System.out.println(car6.getName());
    }

    private static Car createCar(Supplier<Car> carFactory) {
        return carFactory.get();
    }

    private static Car createCar(Function<String, Car> carFactory, String name) {
        return carFactory.apply(name);
    }
}
