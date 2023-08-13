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

        private String name_scientific;
        private String description;
        private String wikipedia_id;
        private String picture_file_name;
        private String picture_content_type;
        private String food_group;

        //old fields
//        private int amount;
//        private String unitMeasure;
//        private String imageUrl;


}
