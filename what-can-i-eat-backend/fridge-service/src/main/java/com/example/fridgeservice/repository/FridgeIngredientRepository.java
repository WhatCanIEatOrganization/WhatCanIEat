package com.example.fridgeservice.repository;

import com.example.fridgeservice.model.FridgeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeIngredientRepository extends JpaRepository<FridgeIngredient, Integer> {
}
