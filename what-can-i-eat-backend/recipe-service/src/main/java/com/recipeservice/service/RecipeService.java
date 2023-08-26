package com.recipeservice.service;

import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.Recipe;

import java.util.List;

public interface RecipeService {

    RecipeDto addNewRecipe(Recipe recipe);

    List<RecipeDto> getRecipesList();
    List<RecipeDto> getFavoriteRecipes();

    void deleteRecipe(int recipeId);

    RecipeDto getRandomRecipe();

}
