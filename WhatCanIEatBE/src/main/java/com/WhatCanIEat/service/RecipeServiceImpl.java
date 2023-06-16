package com.WhatCanIEat.service;

import com.WhatCanIEat.model.Recipe;
import com.WhatCanIEat.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService{


    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
       return recipeRepository.save(recipe);
    }
}
