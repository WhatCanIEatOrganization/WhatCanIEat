package com.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptRequestDto {

    private String model;
    private List<ChatGptMessageDto> messages;

    // getters and setters
}

