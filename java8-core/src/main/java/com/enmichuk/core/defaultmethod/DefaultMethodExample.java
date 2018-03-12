package com.enmichuk.core.defaultmethod;

public class DefaultMethodExample {
    public static void main(String[] args) {
        Hermaphrodite hermaphrodite = new Hermaphrodite();
        hermaphrodite.print();
    }
}

interface Man {
    default void print() {
        System.out.println("I'm a man");
    }
    static void anotherPrint() {
        System.out.println("Also I can do this");
    }
}

interface Woman {
    default void print() {
        System.out.println("I'm a woman");
    }
}

class Hermaphrodite implements Man, Woman {
    public void print() {
        Man.super.print();
        Woman.super.print();
        System.out.println("Because I'm a hermaphrodite");
        Man.anotherPrint();
    }
}