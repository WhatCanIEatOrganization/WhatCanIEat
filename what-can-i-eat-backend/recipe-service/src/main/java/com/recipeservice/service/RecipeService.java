package com.recipeservice.service;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    RecipeDto addNewRecipe(CreateRecipeDto recipeDto);

    List<RecipeDto> findAllRecipes(Pageable pageable);
    List<RecipeDto> getFavoriteRecipes();

    void deleteRecipe(int recipeId);

    RecipeDto getDailyRecipe();

    Optional<RecipeDto> getRecipeById(int id);
    List<RecipeDto> searchRecipesByTags(List<String> ingredients);
    List<IngredientDto> getIngredientsByIds(List<Integer> ingredientIds);
    List<RecipeDto> searchRecipesByFridgeIngredients();
    Mono<List<String>> getFridgeIngredientsNames();

}
