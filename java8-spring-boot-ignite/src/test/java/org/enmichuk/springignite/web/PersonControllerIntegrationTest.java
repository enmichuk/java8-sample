package org.enmichuk.springignite.web;

import org.enmichuk.springignite.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIntegrationTest {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port);
    }

    @Test
    public void createAndGetByFirstName() throws Exception {
        Person person = new Person(200L, "Evgeny", "Michuk", 20000.0, "Good employee");
        ResponseEntity<Person> createResponse = template.postForEntity(base.toString() + "/person", person, Person.class);
        assertThat(createResponse.getBody(), equalTo(person));
        ResponseEntity<Person[]> getResponse = template.getForEntity(base.toString() + "/person/firstname/" + person.firstName, Person[].class);
        assertThat(Arrays.asList(getResponse.getBody()), hasItem(person));
    }
}
