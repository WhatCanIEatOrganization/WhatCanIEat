package com.example.ingredientservice.mapper;

import com.example.ingredientservice.dto.RecipeIngredientDto;
import com.example.ingredientservice.model.RecipeIngredient;

public class RecipeIngredientMapper {

    public static RecipeIngredientDto mapToDto(RecipeIngredient recipeIngredient){
        return RecipeIngredientDto.builder()
                .id(recipeIngredient.getId())
                .name(recipeIngredient.getName())
                .description(recipeIngredient.getDescription())
                .imageUrl(recipeIngredient.getImageUrl())
                .amountWithUnit(recipeIngredient.getAmountWithUnit())
                .build();
    }

    public static RecipeIngredient mapToEntity(RecipeIngredientDto recipeIngredientDto){
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(recipeIngredientDto.id());
        recipeIngredient.setName(recipeIngredientDto.name());
        recipeIngredient.setDescription(recipeIngredientDto.description());
        recipeIngredient.setImageUrl(recipeIngredientDto.imageUrl());
        recipeIngredient.setAmountWithUnit(recipeIngredientDto.amountWithUnit());
        return recipeIngredient;
    }
}
