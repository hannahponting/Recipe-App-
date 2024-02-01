package com.recipe.dataaccess;

import com.recipe.entities.Credential;
import com.recipe.entities.Person;
import com.recipe.services.CredentialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PopulatorTest {
    @BeforeEach
    void setup() {
        personRepository.deleteAll();
    }
    @Autowired
    Populator populator;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    CredentialService credentialService;
    @Autowired
    CredentialRepository credentialRepository;
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testPopulateUsers() throws IOException {
        populator.populateUsers();
        Person ed = personRepository.findPersonByEmail("ed@nerdrecipes.com");
        System.out.println("Ed's ID: " +personRepository.findPersonByEmail("ed@nerdrecipes.com").getId());
        Assertions.assertEquals("W", ed.getLastName());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testPopulateRatings() throws IOException {
        populator.populate();
        populator.populateUsers();
        populator.populateRatings();

        System.out.println("Ed's ID: " +personRepository.findPersonByEmail("ed@nerdrecipes.com").getId());

        ArrayList<Long> ratedRecipes = ratingRepository.findRecipesRatedAtLeast(1.0);
        Assertions.assertTrue(ratedRecipes.size() > 5);
    }
}
