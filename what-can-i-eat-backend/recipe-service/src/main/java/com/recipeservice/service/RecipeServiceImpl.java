package com.recipeservice.service;


import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.IngredientMapper;
import com.recipeservice.mapper.PreparationStepMapper;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.Ingredient;
import com.recipeservice.model.PreparationStep;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.IngredientRepository;
import com.recipeservice.repository.PreparationStepRepository;
import com.recipeservice.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {



    private final RecipeRepository recipeRepository;

    private final PreparationStepRepository preparationStepRepository;
    private final IngredientRepository ingredientRepository;
    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, PreparationStepRepository preparationStepRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.preparationStepRepository = preparationStepRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public RecipeDto addNewRecipe(RecipeDto recipeDto) {
        Recipe savedRecipe = saveRecipe(recipeDto);
        savePreparationSteps(recipeDto, savedRecipe);
        saveIngredients(recipeDto, savedRecipe);
        return RecipeMapper.INSTANCE.recipeToRecipeDto(savedRecipe);
    }

    private Recipe saveRecipe(RecipeDto recipeDto) {
        Recipe mappedRecipe = RecipeMapper.INSTANCE.recipeDtoToRecipe(recipeDto);
        return recipeRepository.save(mappedRecipe);
    }

    private void savePreparationSteps(RecipeDto recipeDto, Recipe savedRecipe) {
        for (PreparationStepDto stepDto : recipeDto.preparationSteps()) {
            PreparationStep step = PreparationStepMapper.INSTANCE.preparationStepDtoToPreparationStep(stepDto);
            step.setRecipe(savedRecipe);
            preparationStepRepository.save(step);
        }
    }

    private void saveIngredients(RecipeDto recipeDto, Recipe savedRecipe) {
        for (IngredientDto ingredientDto : recipeDto.ingredients()) {
            Ingredient ingredient = IngredientMapper.INSTANCE.mapIngredientDtoToIngredient(ingredientDto);
            ingredient.setRecipe(savedRecipe);
            ingredientRepository.save(ingredient);
        }
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

    @Override
    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id).get();
    }

//    @Override
//    public Recipe addIngredientsToRecipe(List<Ingredient> ingredients) {
//        return null;
//    }

}
