package com.WhatCanIEat.model;

import jakarta.persistence.*;

@Entity
@Table(name ="recipe_preparation_steps" )
public class RecipePreparationSteps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id")
    private PreparationStep preparationStep;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

}
