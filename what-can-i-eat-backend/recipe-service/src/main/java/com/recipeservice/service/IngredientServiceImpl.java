package com.recipeservice.service;

import com.recipeservice.model.Ingredient;
import com.recipeservice.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    @Override
    public Ingredient modifyIngredient(Ingredient ingredient) {
        ingredientRepository.deleteById(ingredient.getId());
        return ingredientRepository.save(ingredient);
    }
}
