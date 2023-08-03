package com.recipeservice.service;

import com.recipeservice.model.Ingredient;

import java.util.List;

public interface IngredientService {
    Ingredient addNewIngredient(Ingredient ingredient);

    List<Ingredient> getIngredientList();

    void deleteIngredient(int ingredientId);

    Ingredient modifyIngredient(Ingredient ingredient);
}
