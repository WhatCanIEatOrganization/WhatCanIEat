package com.recipeservice.service;


import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.Ingredient;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecipeServiceImpl implements RecipeService {


    @Autowired
    RecipeRepository recipeRepository;

    @Override
    @Transactional
    public RecipeDto addNewRecipe(Recipe recipe) {
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }
        recipeRepository.save(recipe);
        return RecipeMapper.INSTANCE.recipeToRecipeDto(recipe);
    }

    @Override
    public List<RecipeDto> getRecipesList() {
        return recipeRepository
                .findAll()
                .stream()
                .map(RecipeMapper.INSTANCE::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDto> getFavoriteRecipes() {
        return recipeRepository
                .findAllByFavorite(true)
                .stream()
                .map(RecipeMapper.INSTANCE::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRecipe(int recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public RecipeDto getRandomRecipe() {
        List<Recipe> recipesList = recipeRepository.findAll();
        Random rand = new Random();
        return RecipeMapper.INSTANCE.recipeToRecipeDto(recipesList.get(rand.nextInt(recipesList.size())));
    }

//    @Override
//    public Recipe addIngredientsToRecipe(List<Ingredient> ingredients) {
//        return null;
//    }

}
