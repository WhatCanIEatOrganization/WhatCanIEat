package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ingredients")
public class Ingredient {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false, unique = true)
        private String name;
        private String description;
        private String imageUrl;
        @Enumerated(EnumType.STRING)
        private Category category;

        //old fields
//        private int amount;
//        private String unitMeasure;
//        private String imageUrl;


}
