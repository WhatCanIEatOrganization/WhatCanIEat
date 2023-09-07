package com.example.ingredientservice.dto;

public record BasicIngredientDto(
        int id,
        String name,
        String description,
        int legacyId,
        String imageUrl) {

    public BasicIngredientDto {
        imageUrl = "https://foodb.ca/system/foods/pictures/" + legacyId + "/full/" + legacyId + ".png";
    }
}
