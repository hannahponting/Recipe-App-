package com.recipe.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.recipe.entities.Person;
import com.recipe.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/person")
public class PersonController {
PersonService personService;

@Autowired
public PersonController(PersonService personService){this.personService = personService;}

    @PostMapping("")
    @Operation(summary = "create a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody @JsonView(Person.CreateReadUpdateDelete.class) Person person) {
        Person newPerson;
        try {
            newPerson = personService.addPerson(person);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return newPerson;
    }

    @GetMapping("/{email}")
    @Operation(summary = "get user by email")
    @JsonView(Person.ReadUpdateDelete.class)
    public Person getPersonByEmail(@PathVariable String email){
    Person person = personService.getPersonByEmail(email);
    if(person == null)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    return person;
    }
}
