package com.recipe.services;

import com.recipe.dataaccess.PersonRepository;
import com.recipe.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class PersonService {
    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {this.personRepository = personRepository;}

    public Person addPerson(Person person) {
        if (Stream.of(person.getFirstName(),person.getLastName(),person.getEmail()).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("All fields are mandatory other than ID which is generated by the service.");
        }

        if (person.getId() != null) {
            throw new IllegalArgumentException("The unique ID is generated by the service and does not need to be provided.");
        }
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(person.getEmail()).matches()) {
            throw new IllegalArgumentException("Please check you have entered a valid email address.");
        }

        if (personRepository.findPersonByEmail(person.getEmail()) != null){
            throw new IllegalArgumentException("This email address is already in use, please log in.");
        }
        personRepository.save(person);
        return person;}

    public Person getPersonByEmail(String email) {
        Person person = this.personRepository.findPersonByEmail(email);
        return person;
    }
}
