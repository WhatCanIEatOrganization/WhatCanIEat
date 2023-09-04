package com.example.ingredientservice.service;


import com.example.ingredientservice.dto.IngredientDto;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    IngredientDto addNewIngredient(IngredientDto ingredient);
    void deleteIngredient(int ingredientId);
    Optional<IngredientDto> getIngredientById(int ingredientId);
    List<IngredientDto> findIngredientsById(List<Integer> ids);
}
