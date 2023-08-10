package com.example.ingredientservice.repository;

import com.recipeservice.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
