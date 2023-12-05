package com.recipeservice.service.impl;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.PreparationStepMapper;
import com.recipeservice.model.PreparationStep;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.PreparationStepRepository;
import com.recipeservice.service.PreparationStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PreparationServiceImpl implements PreparationStepService {

    private final PreparationStepRepository preparationStepRepository;

    @Autowired
    public PreparationServiceImpl(PreparationStepRepository preparationStepRepository) {
        this.preparationStepRepository = preparationStepRepository;
    }

    @Override
    @Cacheable(value = "prepSteps", key = "#recipeId")
    public List<PreparationStepDto> getPreparationStepsByRecipeId(Integer recipeId) {
        List<PreparationStep> preparationSteps = preparationStepRepository.findByRecipeId(recipeId);
        return preparationSteps.stream().map(PreparationStepMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PreparationStepDto addPreparationStep(PreparationStepDto preparationStepDto) {
        preparationStepRepository.save(PreparationStepMapper.toEntity(preparationStepDto));
        return preparationStepDto;
    }

    @Override
    public Optional<PreparationStepDto> updatePreparationStep(Integer preparationStepId, PreparationStepDto preparationStepDto) {
        return preparationStepRepository.findById(preparationStepId)
                .map(existingStep -> {
                    existingStep.setStep(preparationStepDto.step());
                    PreparationStep savedStep = preparationStepRepository.save(existingStep);
                    return PreparationStepMapper.toDto(savedStep);
                });
    }
    @Override
    public void deletePreparationStep(Integer stepId) {
        preparationStepRepository.deleteById(stepId);
    }

    @Override
    public Optional<PreparationStepDto> getPreparationStepById(Integer id) {
        Optional<PreparationStep> preparationStep = preparationStepRepository.findById(id);
        return preparationStep.map(PreparationStepMapper::toDto);

    }
}
