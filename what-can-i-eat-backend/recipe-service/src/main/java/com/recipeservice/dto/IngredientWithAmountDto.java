package com.recipeservice.dto;

import lombok.Builder;

@Builder
public record IngredientWithAmountDto(int id, String name, String amount, String ingredientWithAmount, IngredientCategory ingredientCategory, RecipeDto recipeDto) {
}
