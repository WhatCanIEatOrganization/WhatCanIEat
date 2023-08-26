package com.recipeservice.dto;

import java.util.List;

public record RecipeDto(
        int id,
        String name,
        String description,
        boolean favorite,
        String source,
        Integer preptime,
        Integer waittime,
        Integer cooktime,
        Integer calories,
        List<PreparationStepDto> preparationSteps,
        List<IngredientDto> ingredients
) {}
