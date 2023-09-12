package com.example.ingredientservice.service;


import com.example.ingredientservice.dto.RecipeIngredientDto;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientService {
    RecipeIngredientDto addNewIngredient(RecipeIngredientDto ingredient);
    void deleteIngredient(int ingredientId);
    Optional<RecipeIngredientDto> getIngredientById(int ingredientId);
    List<RecipeIngredientDto> findIngredientsById(List<Integer> ids);
}
