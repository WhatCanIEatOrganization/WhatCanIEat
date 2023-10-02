package com.recipeservice.controller;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.service.PreparationStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PreparationStepController {
    private final PreparationStepService preparationStepService;

    @Autowired
    public PreparationStepController(PreparationStepService preparationStepService) {
        this.preparationStepService = preparationStepService;
    }

    @GetMapping
    public ResponseEntity<?> getPreparationStepById(Integer recipeId) {
        List<PreparationStepDto> preparationStepDto = preparationStepService.getPreparationStepsByRecipeId(recipeId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(preparationStepDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePreparationStep(Integer id){
        preparationStepService.deletePreparationStep(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping
    public ResponseEntity<PreparationStepDto> addPreparationStep(@RequestBody PreparationStepDto preparationStepDto){
        preparationStepService.addPreparationStep(preparationStepDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(preparationStepDto);
    }

    @PutMapping("/{preparationStepId}")
    public ResponseEntity<PreparationStepDto> updatePreparationStep(@PathVariable Integer preparationStepId, @RequestBody PreparationStepDto preparationStepDto) {
        Optional<PreparationStepDto> preparationStep = preparationStepService.updatePreparationStep(preparationStepId, preparationStepDto);
        return preparationStep.map(step -> ResponseEntity.status(HttpStatus.OK).body(step))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(preparationStepDto));
    }


}
