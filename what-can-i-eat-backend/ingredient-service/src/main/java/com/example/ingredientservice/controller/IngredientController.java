package com.example.ingredientservice.controller;

import com.example.ingredientservice.model.Ingredient;
import com.example.ingredientservice.service.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientServiceImpl ingredientService;

    @Autowired
    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Ingredient> addNewIngredient(@RequestBody Ingredient requestIngredient) {
        Ingredient savedIngredient = ingredientService.addNewIngredient(requestIngredient);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedIngredient);
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> ingredientList() {
        List<Ingredient> ingredientList = ingredientService.getIngredientList();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientList);
    }

    @DeleteMapping("/{ingredientId}")
    public HttpStatus deleteIngredient(@PathVariable int ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return HttpStatus.NO_CONTENT;
    }

    @PatchMapping
    public ResponseEntity<Ingredient> modifyIngredient(@RequestBody Ingredient modifiedIngredient) {
        Ingredient patched = ingredientService.modifyIngredient(modifiedIngredient);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patched);
    }
}
