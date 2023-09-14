package com.recipeservice.controller;

import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.Recipe;
import com.recipeservice.service.impl.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe")
public class RecipeController {


    private final RecipeServiceImpl recipeService;




    @Autowired
    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
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
                .status(HttpStatus.FOUND)
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

    @GetMapping("/ingredients")
    public List<IngredientDto> getIngredientsByIds(@RequestParam List<Integer> ids) {
        return recipeService.getIngredientsByIds(ids);
    }



    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> searchRecipesByIngredients(@RequestParam List<String> ingredients) {
        List<Recipe> foundRecipes = recipeService.getRecipesByIngredients(ingredients);
        List<RecipeDto> foundRecipeDtos = foundRecipes.stream()
                .map(RecipeMapper.INSTANCE::recipeToRecipeDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(foundRecipeDtos, HttpStatus.OK);
    }
}
