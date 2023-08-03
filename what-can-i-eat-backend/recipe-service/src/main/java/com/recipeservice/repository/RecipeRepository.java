package com.recipeservice.repository;

import com.recipeservice.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByFavorite(boolean bool);
}
