package com.recipeservice.controller;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.service.PreparationStepService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/prep-steps")
@CrossOrigin
public class PreparationStepController {
    private final PreparationStepService preparationStepService;

    @Autowired
    public PreparationStepController(PreparationStepService preparationStepService) {
        this.preparationStepService = preparationStepService;
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "Get preparation steps by recipe ID", description = "Returns a list of preparation steps for a given recipe.")
    public ResponseEntity<List<PreparationStepDto>> getPreparationStepsByRecipeId(@PathVariable int recipeId) {
        List<PreparationStepDto> preparationStepDto = preparationStepService.getPreparationStepsByRecipeId(recipeId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(preparationStepDto);
    }

    @DeleteMapping("/{preparationStepId}")
    @Operation(summary = "Delete a preparation step", description = "Deletes a specific preparation step based on its ID.")
    public ResponseEntity<Void> deletePreparationStep(@PathVariable int preparationStepId){
        preparationStepService.deletePreparationStep(preparationStepId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping
    @Operation(summary = "Add a new preparation step", description = "Adds a new preparation step and returns the added step details.")
    public ResponseEntity<PreparationStepDto> addPreparationStep(@RequestBody PreparationStepDto preparationStepDto){
        preparationStepService.addPreparationStep(preparationStepDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(preparationStepDto);
    }

    @PutMapping("/{preparationStepId}")
    @Operation(summary = "Update a preparation step", description = "Updates a specific preparation step based on its ID and returns the updated step details.")
    public ResponseEntity<PreparationStepDto> updatePreparationStep(@PathVariable Integer preparationStepId, @RequestBody PreparationStepDto preparationStepDto) {
        Optional<PreparationStepDto> preparationStep = preparationStepService.updatePreparationStep(preparationStepId, preparationStepDto);
        return preparationStep
                .map(step -> ResponseEntity.status(HttpStatus.OK).body(step))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(preparationStepDto));
    }


}
