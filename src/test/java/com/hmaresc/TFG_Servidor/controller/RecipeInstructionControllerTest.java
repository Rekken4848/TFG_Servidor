package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.RecipeInstruction;
import com.hmaresc.TFG_Servidor.service.RecipeInstructionService;
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

@WebMvcTest(RecipeInstructionController.class)
@Import(TestSecurityConfig.class)
class RecipeInstructionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private RecipeInstructionService recipeInstructionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipe recipe;
    private RecipeInstruction instruction;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setRecipeName("Tortilla");

        instruction = new RecipeInstruction();
        instruction.setId(1L);
        instruction.setRecipe(recipe);
        instruction.setStepNumber(1);
        instruction.setText("Beat the eggs.");
        instruction.setTime("5 minutes");
    }

    @Test
    void getAllRecipesInstruction_shouldReturnList() throws Exception {
        when(recipeInstructionService.getAllRecipesInstruction()).thenReturn(List.of(instruction));

        mockMvc.perform(get("/recipeinstruction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Beat the eggs."));
    }

    @Test
    void getRecipeInstructionById_shouldReturnOne() throws Exception {
        when(recipeInstructionService.getRecipeInstructionById(1L)).thenReturn(Optional.of(instruction));

        mockMvc.perform(get("/recipeinstruction/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Beat the eggs."));
    }

    @Test
    void getRecipeInstructionById_shouldReturnNotFound() throws Exception {
        when(recipeInstructionService.getRecipeInstructionById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/recipeinstruction/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRecipeInstruction_shouldReturnCreated() throws Exception {
        when(recipeInstructionService.createRecipeInstruction(any(RecipeInstruction.class)))
                .thenReturn(instruction);

        mockMvc.perform(post("/recipeinstruction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instruction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Beat the eggs."));
    }

    @Test
    void updateRecipeInstruction_shouldReturnUpdated() throws Exception {
        instruction.setText("Mix thoroughly.");

        when(recipeInstructionService.updateRecipeInstruction(eq(1L), any(RecipeInstruction.class)))
                .thenReturn(Optional.of(instruction));

        mockMvc.perform(put("/recipeinstruction/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instruction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Mix thoroughly."));
    }

    @Test
    void updateRecipeInstruction_shouldReturnNotFound() throws Exception {
        when(recipeInstructionService.updateRecipeInstruction(eq(999L), any(RecipeInstruction.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/recipeinstruction/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instruction)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRecipeInstruction_shouldReturnNoContent() throws Exception {
        when(recipeInstructionService.deleteRecipeInstruction(1L)).thenReturn(true);

        mockMvc.perform(delete("/recipeinstruction/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecipeInstruction_shouldReturnNotFound() throws Exception {
        when(recipeInstructionService.deleteRecipeInstruction(999L)).thenReturn(false);

        mockMvc.perform(delete("/recipeinstruction/999"))
                .andExpect(status().isNotFound());
    }
}