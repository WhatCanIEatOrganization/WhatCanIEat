package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.service.BasicIngredientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/basic-ingredients")
@CrossOrigin
public class BasicIngredientController {

    private final BasicIngredientService ingredientService;

    @Autowired
    public BasicIngredientController(BasicIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{ingredientId}")
    @Operation(summary = "Get basic ingredient by ID", description = "Retrieve a specific basic ingredient based on its ID.")
    public ResponseEntity<BasicIngredientDto> getRecipeIngredientById(@PathVariable int ingredientId){
        Optional<BasicIngredientDto> ingredient = ingredientService.getBasicIngredientById(ingredientId);
        return ingredient.map(basicIngredientDto -> ResponseEntity
                .status(HttpStatus.OK)
                .body(basicIngredientDto))
                .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    @GetMapping("/name/{ingredientName}")
    @Operation(summary = "Get basic ingredient by name", description = "Retrieve a specific basic ingredient based on its name.")
    public ResponseEntity<BasicIngredientDto> getBasicIngredientByName(@PathVariable String ingredientName){
        Optional<BasicIngredientDto> ingredient = ingredientService.getBasicIngredientByName(ingredientName);
        return ingredient.map(basicIngredientDto -> ResponseEntity
                .status(HttpStatus.OK)
                .body(basicIngredientDto))
                .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    @GetMapping
    @Operation(summary = "Get all basic ingredients", description = "Retrieve a list of all basic ingredients available.")
    public ResponseEntity<List<BasicIngredientDto>> getBasicIngredients() {
        List<BasicIngredientDto> ingredientDtoList = ingredientService.getAllBasicIngredients();
        return new ResponseEntity<>(ingredientDtoList, HttpStatus.OK);
    }
}
