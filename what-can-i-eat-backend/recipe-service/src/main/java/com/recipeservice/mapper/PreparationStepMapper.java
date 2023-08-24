package com.recipeservice.mapper;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.model.PreparationStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PreparationStepMapper {

    PreparationStepMapper INSTANCE = Mappers.getMapper(PreparationStepMapper.class);

    PreparationStepDto preparationStepToPreparationStepDto(PreparationStep preparationStep);

    PreparationStep preparationStepDtoToPreparationStep(PreparationStepDto preparationStepDto);
}