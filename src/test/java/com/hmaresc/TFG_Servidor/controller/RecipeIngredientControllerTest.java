package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.RecipeIngredient;
import com.hmaresc.TFG_Servidor.service.RecipeIngredientService;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeIngredientController.class)
@Import(TestSecurityConfig.class)
class RecipeIngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private RecipeIngredientService recipeIngredientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipe recipe;
    private RecipeIngredient ingredient;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setRecipeName("Tortilla");

        ingredient = new RecipeIngredient();
        ingredient.setId(1L);
        ingredient.setRecipe(recipe);
        ingredient.setName("Egg");
        ingredient.setQuantity("2");
        ingredient.setUnit("units");
    }

    @Test
    void getAllRecipesIngredient_shouldReturnList() throws Exception {
        when(recipeIngredientService.getAllRecipesIngredient()).thenReturn(List.of(ingredient));

        mockMvc.perform(get("/recipeingredient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Egg"));
    }

    @Test
    void getRecipeIngredientById_shouldReturnOne() throws Exception {
        when(recipeIngredientService.getRecipeIngredientById(1L)).thenReturn(Optional.of(ingredient));

        mockMvc.perform(get("/recipeingredient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Egg"));
    }

    @Test
    void createRecipeIngredient_shouldReturnCreated() throws Exception {
        when(recipeIngredientService.createRecipeIngredient(any(RecipeIngredient.class))).thenReturn(ingredient);

        mockMvc.perform(post("/recipeingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Egg"));
    }

    @Test
    void updateRecipeIngredient_shouldReturnUpdated() throws Exception {
        ingredient.setQuantity("3");
        when(recipeIngredientService.updateRecipeIngredient(eq(1L), any(RecipeIngredient.class)))
                .thenReturn(Optional.of(ingredient));

        mockMvc.perform(put("/recipeingredient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value("3"));
    }

    @Test
    void deleteRecipeIngredient_shouldReturnNoContent() throws Exception {
        when(recipeIngredientService.deleteRecipeIngredient(1L)).thenReturn(true);

        mockMvc.perform(delete("/recipeingredient/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecipeIngredient_shouldReturnNotFound() throws Exception {
        when(recipeIngredientService.deleteRecipeIngredient(999L)).thenReturn(false);

        mockMvc.perform(delete("/recipeingredient/999"))
                .andExpect(status().isNotFound());
    }
}