package com.recipeservice.service.impl;

import com.recipeservice.dto.RecipeDto;
import com.recipeservice.repository.RecipeRepository;
import com.recipeservice.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecipeServiceImplTest {

    @MockBean
    RecipeRepository recipeRepository;

    RecipeDto sampleRecipeDto;
    RecipeDto secondSampleRecipeDto;

    @BeforeEach
    public void setUp() {
        sampleRecipeDto = new RecipeDto(
                1,
                "name",
                "description",
                true,
                "source",
                10,
                10,
                10,
                200,
                "imageUrl",
                Collections.emptyList(),
                Collections.emptyList()
        );
        secondSampleRecipeDto = new RecipeDto(
                1,
                "name",
                "description",
                true,
                "source",
                10,
                10,
                10,
                200,
                "imageUrl",
                Collections.emptyList(),
                Collections.emptyList()
        );
    }
    @Test
    void addNewRecipe() {
    }

    @Test
    void getRecipesByIngredients() {
    }

    @Test
    void getRecipesList() {
    }

    @Test
    void getFavoriteRecipes() {
    }

    @Test
    void deleteRecipe() {
    }

    @Test
    void getRandomRecipe() {
    }

    @Test
    void getRecipeById() {
    }

    @Test
    void getIngredientsByIds() {
    }

    @Test
    void updateRecipeImages() {
    }
}