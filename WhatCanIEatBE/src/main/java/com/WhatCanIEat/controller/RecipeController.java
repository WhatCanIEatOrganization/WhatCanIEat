package com.WhatCanIEat.controller;

import com.WhatCanIEat.model.Recipe;
import com.WhatCanIEat.service.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeServiceImpl recipeService;

    @PostMapping
    public ResponseEntity<Recipe> addNewRecipe(@PathVariable Recipe requestRecipe) {
       Recipe savedRecipe = recipeService.addNewRecipe(requestRecipe);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(savedRecipe);
    }
}
