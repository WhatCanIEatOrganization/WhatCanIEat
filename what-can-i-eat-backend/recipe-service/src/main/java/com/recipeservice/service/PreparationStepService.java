package com.recipeservice.service;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.PreparationStep;
import com.recipeservice.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface PreparationStepService {

        List<PreparationStepDto> getPreparationStepsByRecipeId(Integer recipeId);
        PreparationStepDto addPreparationStep(PreparationStepDto preparationStep);
        Optional<PreparationStepDto> updatePreparationStep(Integer id, PreparationStepDto preparationStep);
        void deletePreparationStep(Integer stepId);
        Optional<PreparationStepDto> getPreparationStepById(Integer id);
}
