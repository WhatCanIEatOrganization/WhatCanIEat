package com.recipeservice.service;


import com.recipeservice.model.Recipe;
import com.recipeservice.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RecipeServiceImpl implements RecipeService {


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
    public List<Recipe> getFavoriteRecipes() {
        return recipeRepository.findAllByFavorite(true);
    }

    @Override
    public void deleteRecipe(int recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public Recipe getRandomRecipe() {
        List<Recipe> recipesList = recipeRepository.findAll();
        Random rand = new Random();
        return recipesList.get(rand.nextInt(recipesList.size()));
    }

//    @Override
//    public Recipe addIngredientsToRecipe(List<Ingredient> ingredients) {
//        return null;
//    }

    @Override
    public Recipe modifyRecipe(Recipe recipe) {
        System.out.println(recipe);
        Recipe recipeToModify = recipeRepository.findById(recipe.getId()).get();
        Recipe x = recipeRepository.save(recipeToModify);
        System.out.println(x);
        return x;
    }
}
