package com.example.fridgeservice.model;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "fridge_ingredients")
public class FridgeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant insertDate;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant expiryDate;

    @Column(name = "amount_with_unit")
    private String amountWithUnit;
    @Column(name = "image_url")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Instant insertDate) {
        this.insertDate = insertDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAmountWithUnit() {
        return amountWithUnit;
    }

    public void setAmountWithUnit(String amountWithUnit) {
        this.amountWithUnit = amountWithUnit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
