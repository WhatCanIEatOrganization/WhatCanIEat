package com.WhatCanIEat.service;

import com.WhatCanIEat.model.Recipe;
import com.WhatCanIEat.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{


    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
       return recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipesList() {
        return recipeRepository.findAll();
    }

    @Override
    public void deleteRecipe(int recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
