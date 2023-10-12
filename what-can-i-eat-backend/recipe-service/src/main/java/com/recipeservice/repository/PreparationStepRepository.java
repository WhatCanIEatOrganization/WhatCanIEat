package com.recipeservice.repository;

import com.recipeservice.model.PreparationStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreparationStepRepository extends JpaRepository<PreparationStep, Integer> {
    List<PreparationStep> findByRecipeId(Integer recipeId);
    Optional<PreparationStep> findById(Integer id);
}