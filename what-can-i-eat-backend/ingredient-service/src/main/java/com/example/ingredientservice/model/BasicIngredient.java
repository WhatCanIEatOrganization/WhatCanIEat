package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "basic_ingredients")
public class BasicIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "legacy_id", nullable = false)
    private int legacyId;

    @Transient
    private String imageUrl;

    @PostLoad
    public void fillImageUrl() {
        this.imageUrl = "https://foodb.ca/system/foods/pictures/" + legacyId + "/full/" + legacyId + ".png";
    }

    // Getters and setters

}