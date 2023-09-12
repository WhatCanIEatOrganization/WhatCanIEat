package com.example.ingredientservice.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "fridge_ingredients")
@Getter
@Setter
public class FridgeIngredient extends BasicIngredient {
    @Column(columnDefinition = "TIMESTAMP")
    private Instant insertDate;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant expiryDate;

    @Column(name = "amount_with_unit")
    private String amountWithUnit;
}
