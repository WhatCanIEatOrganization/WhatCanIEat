package com.example.ingredientservice.mapper;


import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.dto.FridgeIngredientDto;
import com.example.ingredientservice.model.FridgeIngredient;

public class FridgeIngredientMapper {

    public static FridgeIngredient basicIngredientDtoToFridgeIngredient(BasicIngredientDto dto) {
        if (dto == null) {
            return null;
        }

        FridgeIngredient fridgeIngredient = new FridgeIngredient();
        fridgeIngredient.setName(dto.name());
        fridgeIngredient.setImageUrl(dto.imageUrl());
        return fridgeIngredient;
    }
        public static FridgeIngredient dtoToFridgeIngredient(FridgeIngredientDto dto) {
            if (dto == null) {
                return null;
            }

            FridgeIngredient fridgeIngredient = new FridgeIngredient();

            fridgeIngredient.setId(dto.id());
            fridgeIngredient.setName(dto.name());
            fridgeIngredient.setInsertDate(dto.insertDate());
            fridgeIngredient.setExpiryDate(dto.expiryDate());
            fridgeIngredient.setAmountWithUnit(dto.amountWithUnit());
            fridgeIngredient.setImageUrl(dto.imageUrl());

            return fridgeIngredient;
        }

        public static FridgeIngredientDto fridgeIngredientToDto(FridgeIngredient fridgeIngredient) {
            if (fridgeIngredient == null) {
                return null;
            }

            return new FridgeIngredientDto(
                    fridgeIngredient.getId(),
                    fridgeIngredient.getName(),
                    null,
                    fridgeIngredient.getImageUrl(),
                    fridgeIngredient.getInsertDate(),
                    fridgeIngredient.getExpiryDate(),
                    fridgeIngredient.getAmountWithUnit()
            );
        }
    }

