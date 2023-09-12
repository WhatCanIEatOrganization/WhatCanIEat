package com.example.ingredientservice.dto;

import lombok.Builder;

@Builder
public record RecipeIngredientDto(int id, String name, String description, String imageUrl, String amountWithUnit) {
}
