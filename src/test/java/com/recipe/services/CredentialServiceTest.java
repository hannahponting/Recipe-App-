package com.recipe.services;

import com.recipe.dataaccess.CredentialRepository;
import com.recipe.dataaccess.PersonRepository;
import com.recipe.entities.PasswordReset;
import com.recipe.entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;

@SpringBootTest
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@TestPropertySource(locations = "classpath:test.properties")
public class CredentialServiceTest {
    @Autowired
    CredentialService credentialService;
    @Autowired
    CredentialRepository repository;
    @Autowired
    PersonRepository personRepository;
    @MockBean
    PasswordResetService passwordResetService;

    @Test
    void testLogin(){
        Person testPerson = new Person();
        testPerson.setEmail("test@test.com");
        if(personRepository.findPersonByEmail("test@test.com") == null){
        personRepository.save(testPerson);}
        Mockito.when(passwordResetService.validReset(any(),any())).thenReturn(true);
        credentialService.generateCredential("test@test.com","TestPassword!", "123456");
        boolean validLogin = credentialService.validLogin("test@test.com", "TestPassword!");
        Assertions.assertTrue(validLogin);

    }
}
