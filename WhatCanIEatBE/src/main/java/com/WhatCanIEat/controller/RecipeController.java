package com.WhatCanIEat.controller;

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
        System.out.println(requestRecipe);
       Recipe savedRecipe = recipeService.addNewRecipe(requestRecipe);
        System.out.println(savedRecipe);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(savedRecipe);
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
}
