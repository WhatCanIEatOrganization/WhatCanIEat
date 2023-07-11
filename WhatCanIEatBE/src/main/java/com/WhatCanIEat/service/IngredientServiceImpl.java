package com.WhatCanIEat.service;

import com.WhatCanIEat.model.Ingredient;
import com.WhatCanIEat.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IngredientServiceImpl implements IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> getIngredientList() {
        return ingredientRepository.findAll();
    }

    @Override
    public void deleteIngredient(int ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }
}
