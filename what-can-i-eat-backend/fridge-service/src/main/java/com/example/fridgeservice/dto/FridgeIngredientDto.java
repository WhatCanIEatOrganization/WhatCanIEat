package com.example.fridgeservice.dto;

import java.time.Instant;

public record FridgeIngredientDto(int id,
                                  String name,
                                  String description,
                                  String imageUrl,
                                  Instant insertDate,
                                  Instant expiryDate,
                                  String amountWithUnit)
{
}
