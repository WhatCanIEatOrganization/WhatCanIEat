package com.example.ingredientservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity()
@Table(name = "foods")
public class Ingredient {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false, unique = true)
        private String name;

        private String name_scientific;
        private String description;
        private String itis_id;
        private String wikipedia_id;
        private String picture_file_name;
        private String picture_content_type;
        private Integer picture_file_size;
        private Date picture_updated_at;
        private Integer legacy_id;
        private String food_group;
        private String food_subgroup;
        private String food_type;
        private Date created_at;
        private Date updated_at;
        private Integer creator_id;
        private Integer updater_id;
        @Column(nullable = false)
        private boolean export_to_afcdb;
        private String category;
        private Integer ncbi_taxonomy_id;
        @Column(nullable = false)
        private boolean export_to_foodb;
        private String public_id;


        private int amount;
        private String unitMeasure;
        private String imageUrl;
        private String wikipediaUrl;
        private Category foodCategory;

}
