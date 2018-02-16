package org.enmichuk.ignite.gettingstarted.binary;

import java.util.concurrent.atomic.AtomicLong;

public class Person {
    private static final AtomicLong ID_GEN = new AtomicLong();

    private Long id;
    private String name;
    private Address address;

    public Person(String name, Address address) {
        this.id = ID_GEN.incrementAndGet();
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override public String toString() {
        return "Person:[id=" + id + ", name=" + name + ", address=" + address + ']';
    }
}
