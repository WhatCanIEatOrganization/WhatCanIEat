package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ingredients")
public class Ingredient {
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
