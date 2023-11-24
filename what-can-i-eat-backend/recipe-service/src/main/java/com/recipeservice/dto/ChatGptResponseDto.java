package com.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptResponseDto {

    private List<Choice> choices;

    // getters and setters

    public static class Choice {

        private int index;
        private ChatGptMessageDto message;

        // getters and setters
    }
}
