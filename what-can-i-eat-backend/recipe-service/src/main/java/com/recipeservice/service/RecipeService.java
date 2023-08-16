package com.recipeservice.service;

import com.recipeservice.model.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe addNewRecipe(Recipe recipe);

    List<Recipe> getRecipesList();
    List<Recipe> getFavoriteRecipes();

    void deleteRecipe(int recipeId);

    Recipe getRandomRecipe();

    Recipe modifyRecipe(Recipe recipe);
}