package com.recipeservice.repository;

import com.recipeservice.model.PreparationStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreparationStepRepository extends JpaRepository<PreparationStep, Integer> {
    List<PreparationStep> findByRecipeId(Integer recipeId);
}