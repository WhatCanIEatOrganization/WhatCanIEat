package com.example.ingredientservice.dto;

public record BasicIngredientDto(
        Integer id,
        String name,
        String description,
        Integer legacyId,
        String imageUrl) {

    public BasicIngredientDto {
        imageUrl = "https://foodb.ca/system/foods/pictures/" + legacyId + "/full/" + legacyId + ".png";
    }
}
