package com.recipeservice.dto;

import java.io.Serializable;

public record PreparationStepDto(Integer id, String step) implements Serializable {
}
