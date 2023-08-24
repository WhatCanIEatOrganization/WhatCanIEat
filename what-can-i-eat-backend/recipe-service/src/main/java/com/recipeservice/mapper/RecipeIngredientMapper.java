package com.recipeservice.mapper;

import com.recipeservice.dto.RecipeIngredientDto;
import com.recipeservice.model.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeIngredientMapper {

    RecipeIngredientMapper INSTANCE = Mappers.getMapper(RecipeIngredientMapper.class);

    RecipeIngredientDto recipeIngredientToRecipeIngredientDto(RecipeIngredient recipeIngredient);

    RecipeIngredient recipeIngredientDtoToRecipeIngredient(RecipeIngredientDto recipeIngredientDto);
}
