package com.example.fridgeservice.dto;

public record BasicIngredientDto(Integer id,
                                 String name,
                                 String description,
                                 Integer legacyId,
                                 String imageUrl) {
}
