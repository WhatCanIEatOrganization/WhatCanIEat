package com.WhatCanIEat.service;

import com.WhatCanIEat.model.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe addNewRecipe(Recipe recipe);

    List<Recipe> getRecipesList();

    void deleteRecipe(int recipeId);

    Recipe getRandomRecipe();
}
