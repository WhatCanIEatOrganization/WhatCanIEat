package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "basic_ingredients")
public class BasicIngredient extends AbstractIngredient {

    @Column(name = "legacy_id")
    private Integer legacyId;

    @Transient
    private String imageUrl;

    @PostLoad
    public void fillImageUrl() {
        this.setImageUrl("https://foodb.ca/system/foods/pictures/" + legacyId + "/full/" + legacyId + ".png");
    }

}