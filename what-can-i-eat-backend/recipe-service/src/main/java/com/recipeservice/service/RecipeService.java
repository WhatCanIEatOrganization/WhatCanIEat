package com.recipeservice.service;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.Recipe;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    RecipeDto addNewRecipe(CreateRecipeDto recipeDto);

    List<RecipeDto> findAllRecipes(Pageable pageable);
    List<RecipeDto> getFavoriteRecipes();

    void deleteRecipe(int recipeId);

    RecipeDto getRandomRecipe();

    Optional<RecipeDto> getRecipeById(int id);
    List<RecipeDto> searchRecipesByTags(List<String> ingredients);
    List<IngredientDto> getIngredientsByIds(List<Integer> ingredientIds);
    List<RecipeDto> searchRecipesByFridgeIngredients();
    List<Recipe> updateRecipeImages();

}
