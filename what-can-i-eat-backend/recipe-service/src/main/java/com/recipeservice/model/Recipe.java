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
    private String instructions;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PreparationStep> preparationSteps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RecipeTag> tags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient_id")
    private List<Integer> ingredients = new ArrayList<>();

}
