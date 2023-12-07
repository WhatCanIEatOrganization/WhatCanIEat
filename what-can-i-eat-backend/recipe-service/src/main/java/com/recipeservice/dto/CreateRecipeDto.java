package com.recipeservice.dto;

import java.util.List;

public record CreateRecipeDto(Integer id,
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
                              List<IngredientDto> ingredients) {
    @Override
    public String toString() {
        return "CreateRecipeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", favorite=" + favorite +
                ", source='" + source + '\'' +
                ", preptime=" + preptime +
                ", waittime=" + waittime +
                ", cooktime=" + cooktime +
                ", calories=" + calories +
                ", imageUrl='" + imageUrl + '\'' +
                ", preparationSteps=" + preparationSteps +
                ", ingredients=" + ingredients +
                '}';
    }
}
