package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "ingredients")
public class RecipeIngredient extends AbstractIngredient {

        @Column(name = "amount_unit")
        private String amountWithUnit;

}
