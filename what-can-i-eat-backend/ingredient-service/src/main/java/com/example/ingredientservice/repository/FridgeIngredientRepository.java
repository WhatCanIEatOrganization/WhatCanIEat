package com.example.ingredientservice.repository;

import com.example.ingredientservice.model.FridgeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeIngredientRepository extends JpaRepository<FridgeIngredient, Integer> {
}
