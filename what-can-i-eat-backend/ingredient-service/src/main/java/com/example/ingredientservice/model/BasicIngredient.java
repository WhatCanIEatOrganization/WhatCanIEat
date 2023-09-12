package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "basic_ingredients")
public class BasicIngredient extends AbstractIngredient {

    @Column(name = "legacy_id", nullable = false)
    private int legacyId;

    @PostLoad
    public void fillImageUrl() {
        this.setImageUrl("https://foodb.ca/system/foods/pictures/" + legacyId + "/full/" + legacyId + ".png");
    }

    // Getters and setters

}