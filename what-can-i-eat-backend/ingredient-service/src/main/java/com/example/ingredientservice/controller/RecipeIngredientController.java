package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.RecipeIngredientDto;
import com.example.ingredientservice.service.RecipeIngredientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/recipe-ingredients")
@CrossOrigin
public class RecipeIngredientController {

    private final RecipeIngredientService ingredientService;

    @Autowired
    public RecipeIngredientController(RecipeIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @Operation(summary = "Add a new recipe ingredient", description = "Adds a new recipe ingredient and returns the added ingredient details.")
    public ResponseEntity<RecipeIngredientDto> addNewRecipeIngredient(@RequestBody RecipeIngredientDto recipeIngredientDto) {
        ingredientService.addNewIngredient(recipeIngredientDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeIngredientDto);
    }

    @GetMapping("/{ingredientId}")
    @Operation(summary = "Get recipe ingredient by ID", description = "Returns a specific recipe ingredient based on its ID.")
    public ResponseEntity<RecipeIngredientDto> getRecipeIngredientById(@PathVariable int ingredientId){
        Optional<RecipeIngredientDto> ingredient = ingredientService.getIngredientById(ingredientId);
        return ingredient.map(recipeIngredientDto -> ResponseEntity
                .status(HttpStatus.OK)
                .body(recipeIngredientDto)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    @PostMapping("/batch")
    @Operation(summary = "Add multiple recipe ingredients", description = "Adds multiple recipe ingredients and returns the list of added ingredients.")
    public ResponseEntity<List<RecipeIngredientDto>> addRecipeIngredients(@RequestBody List<RecipeIngredientDto> recipeIngredientDtos) {
        List<RecipeIngredientDto> savedIngredients = ingredientService.addNewIngredients(recipeIngredientDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredients);
    }

    @GetMapping
    @Operation(summary = "Get recipe ingredients by IDs", description = "Returns a list of recipe ingredients based on the provided IDs.")
    public ResponseEntity<List<RecipeIngredientDto>> getRecipeIngredientsByIds(@RequestParam List<Integer> ids) {
        List<RecipeIngredientDto> ingredientList =  ingredientService.findIngredientsById(ids);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ingredientList);
    }

    @DeleteMapping("/{ingredientId}")
    @Operation(summary = "Delete a recipe ingredient", description = "Deletes a specific recipe ingredient based on its ID.")
    public ResponseEntity<Void> deleteRecipeIngredient(@PathVariable int ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
