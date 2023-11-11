package com.recipeservice.mapper;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.model.PreparationStep;

public class PreparationStepMapper {

    public static PreparationStepDto toDto(PreparationStep preparationStep) {
        if (preparationStep == null) return null;

        return new PreparationStepDto(
                preparationStep.getId(),
                preparationStep.getStep()
        );
    }

    public static PreparationStep toEntity(PreparationStepDto dto) {
        if (dto == null) return null;

        PreparationStep preparationStep = new PreparationStep();
        preparationStep.setId(dto.id());
        preparationStep.setStep(dto.step());
        return preparationStep;
    }
}
