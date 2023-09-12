package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "ingredients")
public class RecipeIngredient {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false, unique = true)
        private String name;
        private String description;
        private String imageUrl;
        @Column(name = "amount_unit")
        private String amountWithUnit;
        @Transient
        private String completeIngredientData;
        @PostLoad
        public void fillCompleteIngredientData() {
                this.completeIngredientData = name + " - " + amountWithUnit;
        }
}
