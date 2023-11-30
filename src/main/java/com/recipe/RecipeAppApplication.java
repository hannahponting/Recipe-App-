package com.recipe;

import com.recipe.dataaccess.Populator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("com.recipe")
public class RecipeAppApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext=SpringApplication.run(RecipeAppApplication.class, args);
//		Populator populator= applicationContext.getBean(Populator.class);
//		populator.populate();

	}


}
