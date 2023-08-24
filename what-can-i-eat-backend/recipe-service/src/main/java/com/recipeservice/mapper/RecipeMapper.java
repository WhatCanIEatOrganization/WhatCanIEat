package com.recipeservice.mapper;

import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PreparationStepMapper.class, RecipeIngredientMapper.class})
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    @Mapping(source = "preparationSteps", target = "preparationSteps")
    @Mapping(source = "recipeIngredients", target = "recipeIngredients")
    RecipeDto recipeToRecipeDto(Recipe recipe);

    @Mapping(source = "preparationSteps", target = "preparationSteps")
    @Mapping(source = "recipeIngredients", target = "recipeIngredients")
    Recipe recipeDtoToRecipe(RecipeDto recipeDto);
}