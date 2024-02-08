//package com.recipe.integrations;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.recipe.entities.Recipe;
//import com.recipe.utilities.Cost;
//import com.recipe.utilities.Difficulty;
//import com.recipe.utilities.MealTime;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@ContextConfiguration
//@AutoConfigureMockMvc
//@Sql("classpath:test-data.sql")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@TestPropertySource(properties = {"spring.sql.init.mode=never"})
//@TestPropertySource(locations="classpath:test.properties")
//
//class GetRecipesWithMockHttpsRequestIT {
//        @Autowired
//        MockMvc mockMvc;
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
//
//
