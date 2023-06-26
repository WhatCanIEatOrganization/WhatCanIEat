package com.WhatCanIEat.repository;

import com.WhatCanIEat.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}
