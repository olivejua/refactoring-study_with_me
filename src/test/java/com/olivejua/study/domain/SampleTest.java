package com.olivejua.study.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class SampleTest {

    @Autowired
    TestEntityManager em;

    @Test
    public void personTest() throws Exception {
        Person person = createPerson();
        Person findPerson = em.find(Person.class, person.getId());

        System.out.println("person = " + person);
        System.out.println("findPerson = " + findPerson);

        assertEquals(person.getId(), findPerson.getId());
        assertEquals(person, findPerson);
    }


    private Person createPerson() {
        Person person = new Person("AAA");
        em.persist(person);
        return person;
    }
}

@Entity
class Person {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    private String name;

    protected Person() {
    }

    public Person(String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
