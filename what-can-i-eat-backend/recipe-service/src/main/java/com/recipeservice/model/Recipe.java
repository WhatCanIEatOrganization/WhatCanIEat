package com.recipeservice.model;

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
@Entity(name = "recipes")
@Table
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int preparationTime;
    private boolean favorite;
    @ElementCollection
    private List<Integer> ingredientsId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<PreparationStep> preparationSteps;
}
