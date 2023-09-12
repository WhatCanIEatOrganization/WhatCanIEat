package com.example.ingredientservice.repository;

import com.example.ingredientservice.model.BasicIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BasicIngredientRepository extends JpaRepository<BasicIngredient, Integer> {
}
