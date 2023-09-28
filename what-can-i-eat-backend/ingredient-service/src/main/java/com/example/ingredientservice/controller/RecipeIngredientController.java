package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.RecipeIngredientDto;
import com.example.ingredientservice.service.impl.RecipeIngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientServiceImpl ingredientService;

    @Autowired
    public RecipeIngredientController(RecipeIngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<RecipeIngredientDto> addNewRecipeIngredient(@RequestBody RecipeIngredientDto recipeIngredientDto) {
        ingredientService.addNewIngredient(recipeIngredientDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeIngredientDto);
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity<?> getRecipeIngredientById(@PathVariable int ingredientId){
        Optional<RecipeIngredientDto> ingredient = ingredientService.getIngredientById(ingredientId);
        if(ingredient.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(ingredient.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<List<RecipeIngredientDto>> addRecipeIngredients(@RequestBody List<RecipeIngredientDto> recipeIngredientDtos) {
        List<RecipeIngredientDto> savedIngredients = ingredientService.addNewIngredients(recipeIngredientDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredients);
    }

    @GetMapping
    public  ResponseEntity<List<RecipeIngredientDto>> getRecipeIngredientsByIds(@RequestParam List<Integer> ids) {
        List<RecipeIngredientDto> ingredientList =  ingredientService.findIngredientsById(ids);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientList);
    }

    @DeleteMapping("/{ingredientId}")
    public HttpStatus deleteRecipeIngredient(@PathVariable int ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return HttpStatus.NO_CONTENT;
    }
}
