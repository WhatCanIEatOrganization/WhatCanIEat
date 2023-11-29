package com.recipeservice.controller;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.model.Recipe;
import com.recipeservice.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recipes")
@CrossOrigin
public class RecipeController {


    private final RecipeService recipeService;
    private static final Logger logger = LogManager.getLogger(RecipeController.class);


    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @Operation(summary = "Create a new recipe", description = "Add a new recipe to the system and return the saved recipe details.")
    public ResponseEntity<RecipeDto> addNewRecipe(@RequestBody CreateRecipeDto recipeDto) {
        RecipeDto savedRecipe = recipeService.addNewRecipe(recipeDto);
        logger.info("Recipe added successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @GetMapping()
    @Operation(summary = "Get paginated and sorted recipes", description = "Retrieves a list of recipes with optional pagination " +
            "(using 'page' and 'size') and sorting (using 'sortBy'). Defaults to sorting by name.")
    public ResponseEntity<List<RecipeDto>> getSortedRecipesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        logger.info("Received request to list all recipes with page: {}, size: {}, sortBy: {}", page, size, sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        List<RecipeDto> recipeList = recipeService.findAllRecipes(pageable);
        logger.info("Retrieved {} recipes successfully", recipeList.size());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipeList);
    }

    @DeleteMapping("/{recipeId}")
    @Operation(summary = "Delete a recipe", description = "Delete a specific recipe based on its ID.")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int recipeId) {
        recipeService.deleteRecipe(recipeId);
        logger.info("Recipe deleted successful");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "Get recipe by ID", description = "Retrieve details of a specific recipe based on its ID.")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable int recipeId) {
        logger.info("Trying to get recipe with id: {}", recipeId);
        Optional<RecipeDto> recipeDto = recipeService.getRecipeById(recipeId);

        return recipeDto.map(dto -> {
            logger.info("Recipe found with id: {}", recipeId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dto);
        }).orElseGet(() -> {
            logger.error("Recipe not found with id: {}", recipeId);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        });
    }

    @GetMapping("/rng")
    @Operation(summary = "Get daily recipe", description = "Retrieve a daily recipe from the system.")
    public ResponseEntity<RecipeDto> getDailyRecipe() {
        logger.info("Attempting to retrieve daily recipe");
        RecipeDto recipe = recipeService.getDailyRecipe();
        logger.info("Get daily recipe successful");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @GetMapping("/favorite")
    @Operation(summary = "Get favorite recipes", description = "Retrieve a list of recipes marked as favorites.")
    public ResponseEntity<List<RecipeDto>> getFavoriteRecipes() {
        logger.info("Attempting to retrieve favorite recipes");
        List<RecipeDto> favoriteRecipes = recipeService.getFavoriteRecipes();
        return ResponseEntity.status(HttpStatus.OK).body(favoriteRecipes);
    }


    @GetMapping("/ingredients")
    @Operation(summary = "Get ingredients by IDs", description = "Retrieve a list of ingredients based on provided IDs.")
    public List<IngredientDto> getIngredientsByIds(@RequestParam List<Integer> ids) {
        logger.info("Attempting to retrieve ingredients for IDs: {}", ids);
        List<IngredientDto> ingredients = recipeService.getIngredientsByIds(ids);
        logger.info("Retrieved {} ingredients successfully", ingredients.size());
        return ingredients;
    }


    @GetMapping("/search")
    @Operation(summary = "Search recipes by ingredients", description = "Retrieve a list of recipes that match the provided ingredients.")
    public ResponseEntity<List<RecipeDto>> searchRecipesByIngredients(@RequestParam List<String> ingredients) {
        logger.info("Attempting to retrieve recipes by ingredients");
        List<RecipeDto> foundRecipes = recipeService.searchRecipesByTags(ingredients);
        logger.info("Search recipes by ingredients successful");
        return new ResponseEntity<>(foundRecipes, HttpStatus.OK);
    }

    @GetMapping("/search/fridge-ingredients")
    public ResponseEntity<List<RecipeDto>> searchRecipesByFridgeIngredients(){
        logger.info("Attempting to retrieve recipes by fridge-ingredients");
        List<RecipeDto> recipes = recipeService.searchRecipesByFridgeIngredients();
        logger.info("Search recipes by fridge-ingredients successful");
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/addImages")
    public List<Recipe> addImagesToRecipes() {
        return recipeService.updateRecipeImages();
    }
}
