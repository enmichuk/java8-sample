package org.enmichuk.springignite.service;

import org.enmichuk.springignite.dao.repository.PersonRepository;
import org.enmichuk.springignite.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public List<Person> getAll() {
        return toList(personRepository.findAll());
    }

    public List<Person> getByFirstName(String name) {
        return personRepository.findByFirstName(name);
    }

    public Cache.Entry<Long, Person> getTopByLastNameLike(String name) {
        return personRepository.findTopByLastNameLike(name);
    }

    public Person save(Person person) {
        Person personToSave = new Person(person);
        return personRepository.save(personToSave.id, personToSave);
    }

    public List<Person> save(Map<Long, Person> persons) {
        return toList(personRepository.save(persons));
    }

    public List<Person> toList(Iterable<Person> iterable) {
        List<Person> target = new ArrayList<>();
        iterable.forEach(target::add);
        return target;
    }
}
