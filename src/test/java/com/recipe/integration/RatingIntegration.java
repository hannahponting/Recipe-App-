package com.recipe.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.entities.Rating;
import com.recipe.entities.Recipe;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    @SpringBootTest
    @ContextConfiguration
    @AutoConfigureMockMvc
    @Sql("classpath:test-data.sql")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    @TestPropertySource(properties = {"spring.sql.init.mode=never"})
    @TestPropertySource(locations = "classpath:test.properties")
    public class RatingIntegration {
        @Autowired
        MockMvc mockMvc;

        @Test
        void submitRating() throws Exception {
            String rating = """
                {
                      "recipeId": 101,
                      "personId": 101,
                      "myRating": 5,
                      "favourite": true
                    }
                """;
            MvcResult result =
                    mockMvc.perform(MockMvcRequestBuilders.post("/api/rating")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(rating))
                            .andExpect(status().isCreated())
                            .andReturn();

            String contentAsJson = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Rating actualRating = mapper.readValue(contentAsJson, Rating.class);
            Assertions.assertEquals(true, actualRating.isFavourite());
            Assertions.assertEquals(5, actualRating.getMyRating());
        }
        @Test
        void submitInvalidRating() throws Exception {
            String rating = """
                {
                      "myRating": 5,
                      "favourite": true
                    }
                """;
            Assertions.assertThrows(ServletException.class, () ->
                    mockMvc.perform(MockMvcRequestBuilders.post("/api/rating")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(rating))
                            .andReturn());
        }
        @Test
        void getRating() throws Exception {
            MvcResult result =
                    (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/rating/101")))
                            .andExpect(status().isOk())
                            .andReturn();

            String contentAsJson = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Double actualRating = mapper.readValue(contentAsJson, Double.class);

            assertEquals(4, actualRating);
        }
        @Test
        void getTopRecipes() throws Exception {
            MvcResult result =
                    (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/top/1")))
                            .andExpect(status().isOk())
                            .andReturn();

            String contentAsJson = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Recipe[] actualRecipes = mapper.readValue(contentAsJson, Recipe[].class);

            assertEquals(101, actualRecipes[0].getId());
        }
        @Test
        void searchRating() throws Exception{
            MvcResult result =
                    (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/min/3")))
                            .andExpect(status().isOk())
                            .andReturn();

            String contentAsJson = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Recipe[] actualRecipes = mapper.readValue(contentAsJson, Recipe[].class);

            assertEquals(101, actualRecipes[0].getId());

        }
        @Test
        void getFavouriteRecipes() throws Exception {
            MvcResult result =
                    (this.mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/favourite/101/page/1/10")))
                            .andExpect(status().isOk())
                            .andReturn();

            String contentAsJson = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(contentAsJson);
            int id = rootNode.get("content").get(0).get("id").asInt();

            assertEquals(102, id);
        }
    }
