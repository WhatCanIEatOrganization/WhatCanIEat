package com.WhatCanIEat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int preparationTime;

    @OneToMany(mappedBy = "recipe")
    List<RecipeIngredients> recipeIngredients;

    @OneToMany(mappedBy = "recipe")
    List<RecipePreparationSteps> recipePreparationSteps;
}
