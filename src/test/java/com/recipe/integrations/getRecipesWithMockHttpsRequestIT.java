package com.recipe.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@Sql("classpath:test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //what does this do?
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
//@WebMvcTest(RecipeController.class)
public class getRecipesWithMockHttpsRequestIT {


        @Autowired
        MockMvc mockMvc;


        private final ObjectMapper mapper = new ObjectMapper();

        @Test
        public void testGettingAllRecipes() throws Exception {
                final String expectedJson = """
                [{"id":101,
                "ingredientsList":"Grilled Lemon Herb Chicken','4 boneless, skinless chicken breasts",
                "instructions":"In a bowl, mix lemon juice, olive oil, minced garlic, oregano, salt, and pepper."}
             ]""";


                MvcResult result =
                        (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes")))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                //.andExpect(content().json(expectedJson))
                                .andReturn();

                String contentAsJson = result.getResponse().getContentAsString();

                Recipe[] actualRecipe = mapper.readValue(contentAsJson,Recipe[].class);

                assertEquals("Grilled Lemon Herb Chicken",actualRecipe[0].getName());
                assertEquals("Vegetarian Quinoa Bowl",actualRecipe[1].getName());


        }


        @Test
        public void testFindRecipeById() throws Exception {

                Long Id= 1L;

                MvcResult result =
                        (this.mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"+Id)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andReturn();

                String contentAsJson = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                Recipe actualRecipe = mapper.readValue(contentAsJson,Recipe.class);

                assertEquals("Grilled Lemon Herb Chicken",actualRecipe.getName());

        }

        @Test
        void testingPRecipeWithNotExistingId() throws Exception {
                Long recipeId = 0L;
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recipes/" + Long.toString(recipeId));
                MvcResult result = mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound())
                        .andReturn();

        }



    }


