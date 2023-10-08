package com.recipeservice.controller;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.Recipe;
import com.recipeservice.service.PexelsService;
import com.recipeservice.service.RecipeService;
import com.recipeservice.service.impl.RecipeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin
public class RecipeController {


    private final RecipeService recipeService;
    private final PexelsService pexelsService;


    @Autowired
    public RecipeController(RecipeServiceImpl recipeService, PexelsService pexelsService) {
        this.recipeService = recipeService;
        this.pexelsService = pexelsService;
    }

    @PostMapping
    @Operation(summary = "Create a new recipe", description = "Add a new recipe to the system and return the saved recipe details.")
    public ResponseEntity<RecipeDto> addNewRecipe(@RequestBody CreateRecipeDto recipeDto) {
        RecipeDto savedRecipe = recipeService.addNewRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all recipes", description = "Retrieve a list of all recipes present in the system.")
    public ResponseEntity<List<RecipeDto>> recipeList() {
        List<RecipeDto> recipeList = recipeService.getRecipesList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipeList);
    }

    @DeleteMapping("/{recipeId}")
    @Operation(summary = "Delete a recipe", description = "Delete a specific recipe based on its ID.")
    public HttpStatus deleteRecipe(@PathVariable int recipeId) {
        recipeService.deleteRecipe(recipeId);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "Get recipe by ID", description = "Retrieve details of a specific recipe based on its ID.")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable int recipeId) {
        Optional<RecipeDto> recipeDto = recipeService.getRecipeById(recipeId);
        return recipeDto.map(dto -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(dto))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @GetMapping("/rng")
    @Operation(summary = "Get random recipe", description = "Retrieve a random recipe from the system.")
    public ResponseEntity<RecipeDto> randomRecipe() {
        RecipeDto recipe = recipeService.getRandomRecipe();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @GetMapping("/favorite")
    @Operation(summary = "Get favorite recipes", description = "Retrieve a list of recipes marked as favorites.")
    public ResponseEntity<List<RecipeDto>> getFavoriteRecipes() {
        List<RecipeDto> favoriteRecipes = recipeService.getFavoriteRecipes();
        return ResponseEntity.status(HttpStatus.OK).body(favoriteRecipes);
    }

    @GetMapping("/ingredients")
    @Operation(summary = "Get ingredients by IDs", description = "Retrieve a list of ingredients based on provided IDs.")
    public List<IngredientDto> getIngredientsByIds(@RequestParam List<Integer> ids) {
        return recipeService.getIngredientsByIds(ids);
    }


    @GetMapping("/search")
    @Operation(summary = "Search recipes by ingredients", description = "Retrieve a list of recipes that match the provided ingredients.")
    public ResponseEntity<List<RecipeDto>> searchRecipesByIngredients(@RequestParam List<String> ingredients) {
        List<Recipe> foundRecipes = recipeService.getRecipesByIngredients(ingredients);
        List<RecipeDto> foundRecipeDtos = foundRecipes.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(foundRecipeDtos, HttpStatus.OK);
    }

    @GetMapping("/addImages")
    public List<Recipe> addImagesToRecipe() {
        return recipeService.updateRecipeImages();
    }
}
