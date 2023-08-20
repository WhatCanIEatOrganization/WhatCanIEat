package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.IngredientDto;
import com.example.ingredientservice.mapper.IngredientMapper;
import com.example.ingredientservice.model.Ingredient;
import com.example.ingredientservice.service.IngredientServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
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
                    .body(ingredient);
        }
    }

    @GetMapping
    public ResponseEntity<List<IngredientDto>> ingredientList() {
        List<IngredientDto> ingredientList = ingredientService.getIngredientList();
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
