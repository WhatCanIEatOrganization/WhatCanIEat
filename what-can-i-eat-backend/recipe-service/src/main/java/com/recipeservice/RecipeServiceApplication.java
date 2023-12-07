package com.recipeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class RecipeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipeServiceApplication.class, args);
	}
}
