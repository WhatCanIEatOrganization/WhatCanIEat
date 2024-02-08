//package com.recipeservice.service.impl;
//
//import com.recipeservice.dto.chatgpt.ChatGptResponseDto;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.web.client.RestTemplate;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//public class ChatGptServiceImplTest {
//    @Autowired
//    private ChatGptServiceImpl chatGptService;
//
//    @MockBean
//    private RestTemplate restTemplate;
//
//    @MockBean
//    private RecipeServiceImpl recipeService;
//
//
//    @Test
//    void buildPromptCreatesCorrectPrompt() {
//        String ingredients = "Tomato, Onion";
//        String expectedPrompt = "Create a complete recipe in English using these ingredients: Tomato, Onion. ...";
//
//        String actualPrompt = chatGptService.buildPrompt(ingredients);
//
//        assertEquals(expectedPrompt, actualPrompt);
//    }
//
//    @Test
//    void createRecipeFromChatGptReturnsRecipeOnSuccess() {
//        when(recipeService.getFridgeIngredientsNames()).thenReturn(Mono.just(List.of("Tomato", "Onion")));
//        when(restTemplate.postForObject(anyString(), any(), eq(ChatGptResponseDto.class)))
//                .thenReturn(mockChatGptResponse);
//
//        Optional<CreateRecipeDto> result = chatGptService.createRecipeFromChatGpt();
//
//        assertTrue(result.isPresent());
//        // Dodatkowe asercje dotyczące zawartości result
//    }
//
//
//}