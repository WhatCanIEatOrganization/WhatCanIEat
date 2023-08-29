package com.recipeservice.controller;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.PreparationStepMapper;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.PreparationStep;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.IngredientRepository;
import com.recipeservice.repository.PreparationStepRepository;
import com.recipeservice.service.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {


    private final RecipeServiceImpl recipeService;
    private final WebClient.Builder webClientBuilder;



    @Autowired
    public RecipeController(RecipeServiceImpl recipeService, WebClient.Builder webClientBuilder) {
        this.recipeService = recipeService;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping
    public ResponseEntity<RecipeDto> addNewRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto savedRecipe = recipeService.addNewRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> recipeList() {
        List<RecipeDto> recipeList = recipeService.getRecipesList();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeList);
    }

    @DeleteMapping("/{recipeId}")
    public HttpStatus deleteRecipe(@PathVariable int recipeId) {
        recipeService.deleteRecipe(recipeId);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/{recipeId}")
    public Recipe getRecipeById(@PathVariable int recipeId){
        return recipeService.getRecipeById(recipeId);
    }

    @GetMapping("/rng")
    public ResponseEntity<RecipeDto> randomRecipe() {
        RecipeDto recipe = recipeService.getRandomRecipe();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @GetMapping("/favorite")
    public ResponseEntity<List<RecipeDto>> getFavoriteRecipes() {
        List<RecipeDto> favoriteRecipes = recipeService.getFavoriteRecipes();
        return ResponseEntity.status(HttpStatus.OK).body(favoriteRecipes);
    }
}
