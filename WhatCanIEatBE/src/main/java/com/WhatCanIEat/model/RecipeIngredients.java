package com.WhatCanIEat.model;

import jakarta.persistence.*;

@Entity
@Table(name ="recipe_ingredients" )
public class RecipeIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Column(name = "ingredient_quantity")
    private int ingredientQuantity;
}
