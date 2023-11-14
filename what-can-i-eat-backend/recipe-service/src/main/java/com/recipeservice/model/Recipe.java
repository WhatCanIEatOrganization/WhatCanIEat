package com.recipeservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String imageUrl;


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
    private Set<PreparationStep> preparationSteps = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<RecipeTag> tags = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient_id")
    private List<Integer> ingredients = new ArrayList<>();

}
