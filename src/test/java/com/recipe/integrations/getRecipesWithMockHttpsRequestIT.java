package com.recipe.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@Sql("classpath:test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //what does this do?
@TestPropertySource(properties = {"spring.sql.init.mode=never"})

public class getRecipesWithMockHttpsRequestIT {


        @Autowired
        MockMvc mockMvc;

        private final ObjectMapper mapper = new ObjectMapper();

        @Test
        public void testGettingAllRecipes() throws Exception {

        }

        @Test
        public void testFindRecipesById() throws Exception {

        }



    }


