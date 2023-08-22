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
                .amountWithUnit(ingredient.getAmountWithUnit())
                .build();
    }

    public static Ingredient mapToEntity(IngredientDto ingredientDto){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDto.id());
        ingredient.setName(ingredientDto.name());
        ingredient.setDescription(ingredientDto.description());
        ingredient.setImageUrl(ingredientDto.imageUrl());
        ingredient.setIngredientCategory(ingredientDto.ingredientCategory());
        ingredient.setAmountWithUnit(ingredientDto.amountWithUnit());
        return ingredient;
    }
}
