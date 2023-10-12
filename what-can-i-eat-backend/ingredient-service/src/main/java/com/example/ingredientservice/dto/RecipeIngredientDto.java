package com.example.ingredientservice.dto;

import lombok.Builder;

@Builder
public record RecipeIngredientDto(Integer id, String name, String description, String imageUrl, String amountWithUnit) {
}
