package org.enmichuk.springignite.web;

import org.enmichuk.springignite.model.Person;
import org.enmichuk.springignite.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping("/person")
    public List<Person> getAll() {
        return personService.getAll();
    }

    @RequestMapping("/person/firstname/{name}")
    public List<Person> personByFirstName(@PathVariable("name")String name) {
        return personService.getByFirstName(name);
    }

    @RequestMapping("/person/lastname/{name}")
    public Person personByLastName(@PathVariable("name")String name) {
        return personService.getTopByLastNameLike(name).getValue();
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST)
    public Person save(@RequestBody Person person) {
        return personService.save(person);
    }
}
