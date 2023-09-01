package com.example.ingredientservice.service;


import com.example.ingredientservice.dto.IngredientDto;
import com.example.ingredientservice.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    IngredientDto addNewIngredient(IngredientDto ingredient);

    List<IngredientDto> getIngredientList();

    void deleteIngredient(int ingredientId);

    Optional<IngredientDto> getIngredientById(int ingredientId);
    List<IngredientDto> findIngredientsById(List<Integer> ids);
}
