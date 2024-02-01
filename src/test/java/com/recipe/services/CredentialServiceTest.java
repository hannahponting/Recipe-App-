package com.recipe.services;

import com.recipe.dataaccess.CredentialRepository;
import com.recipe.dataaccess.PersonRepository;
import com.recipe.entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;

@SpringBootTest
public class CredentialServiceTest {
    @Autowired
    CredentialService credentialService;
    @Autowired
    CredentialRepository repository;
    @MockBean
    PersonRepository personRepository;
    @BeforeEach
    void setup(){
        reset(this.personRepository);
    }

    @Test
    void testLogin(){
        Person testPerson = new Person();
        testPerson.setEmail("test@test.com");
        Mockito.when(personRepository.findPersonByEmail(any())).thenReturn(testPerson);
        credentialService.generateCredential("test@test.com","TestPassword!");
        boolean validLogin = credentialService.validLogin("test@test.com", "TestPassword!");
        Assertions.assertTrue(validLogin);

    }
}
