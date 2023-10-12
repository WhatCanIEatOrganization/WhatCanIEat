package com.example.ingredientservice.service.impl;



import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.dto.FridgeIngredientDto;
import com.example.ingredientservice.mapper.FridgeIngredientMapper;
import com.example.ingredientservice.model.FridgeIngredient;
import com.example.ingredientservice.repository.FridgeIngredientRepository;
import com.example.ingredientservice.service.BasicIngredientService;
import com.example.ingredientservice.service.FridgeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FridgeIngredientServiceImpl implements FridgeIngredientService {
    private final FridgeIngredientRepository ingredientRepository;
    private final BasicIngredientService basicIngredientService;



    @Autowired
    public FridgeIngredientServiceImpl(FridgeIngredientRepository ingredientRepository, BasicIngredientService basicIngredientService) {
        this.ingredientRepository = ingredientRepository;
        this.basicIngredientService = basicIngredientService;
    }


    @Override
    public FridgeIngredientDto addFridgeIngredient(FridgeIngredientDto fridgeIngredientDto) {
        Optional<BasicIngredientDto> basicIngredientDto = basicIngredientService.getBasicIngredientByName(fridgeIngredientDto.name());
        if(basicIngredientDto.isPresent()){
            FridgeIngredient fridgeIngredient = FridgeIngredientMapper.basicIngredientDtoToFridgeIngredient(basicIngredientDto.get());
            fridgeIngredient.setAmountWithUnit(fridgeIngredientDto.amountWithUnit());
            return FridgeIngredientMapper.fridgeIngredientToDto(ingredientRepository
                    .save(fridgeIngredient));
        }
        else {
            FridgeIngredient newIngredient = FridgeIngredientMapper.dtoToFridgeIngredient(fridgeIngredientDto);
            return FridgeIngredientMapper.fridgeIngredientToDto(ingredientRepository.save(newIngredient));
        }
    }

    @Override
    public List<FridgeIngredientDto> getAllFridgeIngredients() {
        List<FridgeIngredient> ingredients = ingredientRepository.findAll();
        return ingredients
                .stream()
                .map(FridgeIngredientMapper::fridgeIngredientToDto)
                .collect(Collectors.toList());
    }


}
