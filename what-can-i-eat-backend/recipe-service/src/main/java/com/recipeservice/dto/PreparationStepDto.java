package com.recipeservice.dto;

import java.io.Serializable;

public record PreparationStepDto(Integer id, String step) implements Serializable {

    @Override
    public String toString() {
        return "PreparationStepDto{" +
                "id=" + id +
                ", step='" + step + '\'' +
                '}';
    }
}
