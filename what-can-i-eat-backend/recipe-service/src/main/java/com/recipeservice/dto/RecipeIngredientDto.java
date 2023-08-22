package com.recipeservice.dto;

import lombok.Builder;

@Builder
public record RecipeIngredientDto(int id, String completeIngredientData, IngredientCategory ingredientCategory, RecipeDto recipeDto) {
}
