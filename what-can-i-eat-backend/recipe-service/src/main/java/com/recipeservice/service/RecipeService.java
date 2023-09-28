package com.recipeservice.service;

import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    RecipeDto addNewRecipe(RecipeDto recipe);

    List<RecipeDto> getRecipesList();
    List<RecipeDto> getFavoriteRecipes();

    void deleteRecipe(int recipeId);

    RecipeDto getRandomRecipe();

    Optional<RecipeDto> getRecipeById(int id);
    List<Recipe> getRecipesByIngredients(List<String> ingredients);
    List<IngredientDto> getIngredientsByIds(List<Integer> ingredientIds);

}
