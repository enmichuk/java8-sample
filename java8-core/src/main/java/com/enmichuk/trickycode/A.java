package com.enmichuk.trickycode;

public class A {
    public static void show(){
        System.out.println("Static method called");
    }

    public static void main(String[] args)  {
        A obj = null;
        obj.show();
    }
}
