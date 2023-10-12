package com.example.ingredientservice.repository;

import com.example.ingredientservice.model.BasicIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BasicIngredientRepository extends JpaRepository<BasicIngredient, Integer> {
    Optional<BasicIngredient> findBasicIngredientByName(String name);
}
