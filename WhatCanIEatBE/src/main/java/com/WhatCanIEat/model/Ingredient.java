package com.WhatCanIEat.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

  @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredients> recipeIngredients;



}
