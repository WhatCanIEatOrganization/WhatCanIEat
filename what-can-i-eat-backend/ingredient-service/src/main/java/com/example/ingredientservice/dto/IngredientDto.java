package com.example.ingredientservice.dto;

import com.example.ingredientservice.model.IngredientCategory;
import lombok.Builder;

@Builder
public record IngredientDto(int id, String name, String description, String imageUrl, IngredientCategory ingredientCategory) {
}
