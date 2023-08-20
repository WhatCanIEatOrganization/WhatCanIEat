package com.example.ingredientservice.mapper;

import com.example.ingredientservice.dto.IngredientDto;
import com.example.ingredientservice.model.Ingredient;

public class IngredientMapper {

    public static IngredientDto mapToDto(Ingredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .description(ingredient.getDescription())
                .imageUrl(ingredient.getImageUrl())
                .ingredientCategory(ingredient.getIngredientCategory())
                .build();
    }

    public static Ingredient mapToEntity(IngredientDto ingredientDto){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredient.getId());
        ingredient.setName(ingredient.getName());
        ingredient.setDescription(ingredient.getDescription());
        ingredient.setImageUrl(ingredient.getImageUrl());
        ingredient.setIngredientCategory(ingredient.getIngredientCategory());
        return ingredient;
    }
}
