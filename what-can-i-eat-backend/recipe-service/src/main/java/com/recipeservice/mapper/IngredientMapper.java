package com.recipeservice.mapper;

import com.recipeservice.dto.IngredientDto;
import com.recipeservice.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);


    IngredientDto mapIngredientToIngredientDto(Ingredient ingredient);
    Ingredient mapIngredientDtoToIngredient(IngredientDto ingredientDto);
}
