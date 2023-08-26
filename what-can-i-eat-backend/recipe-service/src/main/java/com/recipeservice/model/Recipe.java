package com.recipeservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private boolean favorite;



    // fields from import
    private String source;
    private Integer preptime;
    private Integer waittime;
    private Integer cooktime;
    private Integer servings;
    private String comments;
    private Integer calories;
    private Integer fat;
    private Integer satfat;
    private Integer carbs;
    private Integer fiber;
    private Integer sugar;
    private Integer protein;
    private String instructions;

    // relations

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<PreparationStep> preparationSteps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients = new ArrayList<>();

}
