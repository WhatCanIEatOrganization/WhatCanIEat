package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.dto.RecipeIngredientDto;
import com.example.ingredientservice.service.BasicIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BasicIngredientController {

    private final BasicIngredientService ingredientService;

    @Autowired
    public BasicIngredientController(BasicIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{ingredientId}")
    public ResponseEntity<?> getRecipeIngredientById(@PathVariable int ingredientId){
        Optional<BasicIngredientDto> ingredient = ingredientService.getBasicIngredientById(ingredientId);
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
}
