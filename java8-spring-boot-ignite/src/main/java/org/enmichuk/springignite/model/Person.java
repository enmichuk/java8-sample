package org.enmichuk.springignite.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;

/**
 * [{"id":2,"orgId":2000,"firstName":"Brad","lastName":"Pitt","resume":"Worked for Oracle","salary":16000.0}]
 */
public class Person implements Serializable {
    private static final AtomicLong ID_GEN = new AtomicLong();

    @QuerySqlField(index = true)
    public Long id;

    @QuerySqlField(index = true)
    public Long orgId;

    @QuerySqlField
    public String firstName;

    @QuerySqlField
    public String lastName;

    @QueryTextField
    public String resume;

    @QuerySqlField(index = true)
    public double salary;

    private transient AffinityKey<Long> key;

    public Person() {
        // No-op.
    }

    public Person(Person person) {
        this(person.orgId, person.firstName, person.lastName, person.salary, person.resume);
    }

    public Person(Long orgId, String firstName, String lastName, double salary, String resume) {
        id = ID_GEN.incrementAndGet();
        this.orgId = orgId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.resume = resume;
    }

    public Person(Long id, Long orgId, String firstName, String lastName, double salary, String resume) {
        this.id = id;
        this.orgId = orgId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.resume = resume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(person.salary, salary) == 0 &&
                Objects.equals(orgId, person.orgId) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(resume, person.resume) &&
                Objects.equals(key, person.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId, firstName, lastName, resume, salary, key);
    }

    public Person(Long id, String firstName, String lastName) {

        this.id = id;

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AffinityKey<Long> key() {
        if (key == null)
            key = new AffinityKey<>(id, orgId);

        return key;
    }

    @Override public String toString() {
        return "Person [id=" + id +
                ", orgId=" + orgId +
                ", lastName=" + lastName +
                ", firstName=" + firstName +
                ", salary=" + salary +
                ", resume=" + resume + ']';
    }
}