package com.WhatCanIEat.controller;

import com.WhatCanIEat.dto.CreateRecipeIngredientsListDTO;
import com.WhatCanIEat.model.Recipe;
import com.WhatCanIEat.service.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeServiceImpl recipeService;

    @PostMapping
    public ResponseEntity<Recipe> addNewRecipe(@RequestBody Recipe requestRecipe) {
        Recipe savedRecipe = recipeService.addNewRecipe(requestRecipe);

       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(savedRecipe);
    }

    @PostMapping("/list")
    public HttpStatus addRecipeIngredientsList(@RequestBody CreateRecipeIngredientsListDTO recipeIngredientsListDTO) {
        return HttpStatus.OK;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> recipeList() {
        List<Recipe> recipeList = recipeService.getRecipesList();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeList);
    }

    @DeleteMapping("/{recipeId}")
    public HttpStatus deleteRecipe(@PathVariable int recipeId) {
        recipeService.deleteRecipe(recipeId);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/rng")
    public ResponseEntity<Recipe> randomRecipe() {
        Recipe recipe = recipeService.getRandomRecipe();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @PatchMapping
    public ResponseEntity<Recipe> modifyRecipe(@RequestBody Recipe recipe) {
        Recipe patched = recipeService.modifyRecipe(recipe);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patched);
    }

    @GetMapping("/favorite")
    public ResponseEntity<List<Recipe>> getFavoriteRecipes() {
        List<Recipe> favoriteRecipes = recipeService.getFavoriteRecipes();
        return ResponseEntity.status(HttpStatus.OK).body(favoriteRecipes);
    }
}
