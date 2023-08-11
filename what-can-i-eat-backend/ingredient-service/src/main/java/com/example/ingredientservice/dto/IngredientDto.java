package com.example.ingredientservice.dto;

public record IngredientDto(int id, String name, String description, String foodGroup, int amount, String unitMeasure, String imageUrl) {
}
