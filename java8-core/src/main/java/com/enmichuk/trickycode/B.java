package com.enmichuk.trickycode;

public class B {
    B b = new B();

    public int show(){
        return (true ? null : 0);
    }

    public static void main(String[] args)  {
        B b = new B();
        b.show();
    }
}