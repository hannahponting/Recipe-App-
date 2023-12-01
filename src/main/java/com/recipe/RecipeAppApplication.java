package com.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.recipe")
public class RecipeAppApplication {

	public static void main(String[] args){
		SpringApplication.run(RecipeAppApplication.class, args);
	}

}
