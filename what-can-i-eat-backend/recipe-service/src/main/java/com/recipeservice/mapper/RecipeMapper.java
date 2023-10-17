package com.recipeservice.mapper;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.PreparationStep;
import com.recipeservice.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDto toDto(Recipe recipe) {
        if (recipe == null) return null;
        return new RecipeDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.isFavorite(),
                recipe.getSource(),
                recipe.getPreptime(),
                recipe.getWaittime(),
                recipe.getCooktime(),
                recipe.getCalories(),
                recipe.getImageUrl(),
                recipe.getPreparationSteps().stream()
                        .map(PreparationStepMapper::toDto)
                        .collect(Collectors.toList()),
                new ArrayList<>(recipe.getIngredients())
        );
    }

    public static Recipe toEntity(RecipeDto dto) {
        if (dto == null) return null;
        Recipe recipe = new Recipe();
        recipe.setId(dto.id());
        recipe.setName(dto.name());
        recipe.setDescription(dto.description());
        recipe.setFavorite(dto.favorite());
        recipe.setSource(dto.source());
        recipe.setPreptime(dto.preptime());
        recipe.setWaittime(dto.waittime());
        recipe.setCooktime(dto.cooktime());
        recipe.setCalories(dto.calories());
        recipe.setImageUrl(dto.imageUrl());
        recipe.setPreparationSteps(dto.preparationSteps().stream()
                .map(PreparationStepMapper::toEntity)
                .collect(Collectors.toList()));
        recipe.setIngredients(new ArrayList<>(dto.ingredients()));

        return recipe;
    }

    public static Recipe createRecipeDtoToEntity(CreateRecipeDto dto) {
        if (dto == null) return null;
        Recipe recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setDescription(dto.description());
        recipe.setFavorite(dto.favorite());
        recipe.setPreptime(dto.preptime());
        recipe.setWaittime(dto.waittime());
        recipe.setCooktime(dto.cooktime());
        recipe.setCalories(dto.calories());
        recipe.setSource(dto.source());
        recipe.setImageUrl(dto.imageUrl());
        recipe.setPreparationSteps(dto.preparationSteps().stream()
                .map(stepDto -> {
                    PreparationStep step = PreparationStepMapper.toEntity(stepDto);
                    step.setRecipe(recipe);
                    return step;
                })
                .collect(Collectors.toList()));
        List<Integer> ingredientIds = dto.ingredients().stream()
                .map(IngredientDto::id)
                .collect(Collectors.toList());
        recipe.setIngredients(ingredientIds);

        return recipe;
    }

}