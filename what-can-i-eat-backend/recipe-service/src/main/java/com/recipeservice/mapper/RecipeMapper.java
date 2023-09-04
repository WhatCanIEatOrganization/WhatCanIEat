package com.recipeservice.mapper;

import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PreparationStepMapper.class})
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    @Mapping(source = "preparationSteps", target = "preparationSteps")
    RecipeDto recipeToRecipeDto(Recipe recipe);

    @Mapping(source = "preparationSteps", target = "preparationSteps")
    Recipe recipeDtoToRecipe(RecipeDto recipeDto);
}