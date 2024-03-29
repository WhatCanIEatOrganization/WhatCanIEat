package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ingredients")
public class RecipeIngredient extends AbstractIngredient {

        @Column(name = "amount_unit")
        private String amountWithUnit;

}
