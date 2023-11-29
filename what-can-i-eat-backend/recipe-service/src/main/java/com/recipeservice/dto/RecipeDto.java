package com.recipeservice.dto;

import lombok.NoArgsConstructor;

import java.io.Serializable;
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
        String imageUrl,
        List<PreparationStepDto> preparationSteps,
        List<Integer> ingredients
) implements Serializable {
}
