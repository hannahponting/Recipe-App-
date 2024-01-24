package com.recipe;

import com.recipe.dataaccess.Populator;
import com.recipe.dataaccess.RecipeRepository;
import com.recipe.entities.Recipe;
import com.recipe.services.RecipeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;



@SpringBootApplication
@ComponentScan("com.recipe")
public class RecipeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeAppApplication.class, args);
	}

}
