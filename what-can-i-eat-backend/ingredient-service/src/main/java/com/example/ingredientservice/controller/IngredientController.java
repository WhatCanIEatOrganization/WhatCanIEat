package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.IngredientDto;
import com.example.ingredientservice.service.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientServiceImpl ingredientService;

    @Autowired
    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<IngredientDto> addNewIngredient(@RequestBody IngredientDto ingredientDto) {
        ingredientService.addNewIngredient(ingredientDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientDto);
    }

    @GetMapping("/{ingredientId}")
    public ResponseEntity<?> getIngredientById(@PathVariable int ingredientId){
        Optional<IngredientDto> ingredient = ingredientService.getIngredientById(ingredientId);
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
    public ResponseEntity<List<IngredientDto>> addIngredients(@RequestBody List<IngredientDto> ingredientDtos) {
        List<IngredientDto> savedIngredients = ingredientService.addNewIngredients(ingredientDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIngredients);
    }

    @GetMapping
    public  ResponseEntity<List<IngredientDto>> getIngredientsByIds(@RequestParam List<Integer> ids) {
        List<IngredientDto> ingredientList =  ingredientService.findIngredientsById(ids);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientList);
    }

    @DeleteMapping("/{ingredientId}")
    public HttpStatus deleteIngredient(@PathVariable int ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return HttpStatus.NO_CONTENT;
    }
}
