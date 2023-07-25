package com.WhatCanIEat.service;

import com.WhatCanIEat.model.Ingredient;

import java.util.List;

public interface IngredientService {
    Ingredient addNewIngredient(Ingredient ingredient);

    List<Ingredient> getIngredientList();

    void deleteIngredient(int ingredientId);

    Ingredient modifyIngredient(Ingredient ingredient);
}
