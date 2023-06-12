package org.goafabric.core.data.controller;

import jakarta.validation.Valid;
import org.goafabric.core.data.controller.dto.Person;
import org.goafabric.core.data.logic.PersonLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PersonController {
    private final PersonLogic personLogic;

    public PersonController(PersonLogic personLogic) {
        this.personLogic = personLogic;
    }

    @GetMapping("getById/{id}")
    public Person getById(@PathVariable("id") String id) {
        return personLogic.getById(id);
    }

    @GetMapping("findAll")
    public List<Person> findAll() {
        return personLogic.findAll();
    }

    @GetMapping("findByFirstName")
    public List<Person> findByFirstName(@RequestParam("firstName") String firstName) {
        return personLogic.findByFirstName(firstName);
    }

    @GetMapping("findByLastName")
    public List<Person> findByLastName(@RequestParam("lastName") String lastName) {
        return personLogic.findByLastName(lastName);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person save(@RequestBody @Valid Person person) {
        return personLogic.save(person);
    }

}