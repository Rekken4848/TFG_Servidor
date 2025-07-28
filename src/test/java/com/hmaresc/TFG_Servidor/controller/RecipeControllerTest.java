package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.service.RecipeService;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import com.hmaresc.TFG_Servidor.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
@Import(TestSecurityConfig.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private final User user = new User();
    private final Recipe recipe = new Recipe();

    @BeforeEach
    void setUp() {
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setUsername("user");

        recipe.setId(1L);
        recipe.setRecipeName("Tortilla");
        recipe.setRecipeCategory("Main");
        recipe.setPrepTime(10);
        recipe.setCookTime(15);
        recipe.setServings(2);
        recipe.setUser(user);
        recipe.setIngredients(new ArrayList<>());
        recipe.setInstructions(new ArrayList<>());
    }

    @Test
    void getAllRecipes_shouldReturnOk() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(List.of(recipe));

        mockMvc.perform(get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeName").value("Tortilla"));
    }

    @Test
    void getRecipeById_shouldReturnRecipe() throws Exception {
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(recipe));

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Tortilla"));
    }

    @Test
    void getRecipeByEmail_shouldReturnRecipes() throws Exception {
        when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(recipeService.getRecipeByUser(user)).thenReturn(Optional.of(List.of(recipe)));

        mockMvc.perform(get("/recipe/myrecipes/user@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recipeName").value("Tortilla"));
    }

    @Test
    void createRecipe_shouldReturnCreatedRecipe() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "fake-image".getBytes());
        MockMultipartFile recipePart = new MockMultipartFile("recipe", "", "application/json", objectMapper.writeValueAsBytes(recipe));

        when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(recipeService.createRecipe(any(Recipe.class))).thenReturn(recipe);

        mockMvc.perform(multipart("/recipe/user@example.com")
                        .file(image)
                        .file(recipePart))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Tortilla"));
    }

    @Test
    void updateRecipe_shouldReturnUpdatedRecipe() throws Exception {
        recipe.setRecipeName("Updated Tortilla");

        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "updated-image".getBytes());
        MockMultipartFile recipePart = new MockMultipartFile("recipe", "", "application/json", objectMapper.writeValueAsBytes(recipe));

        when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(recipeService.updateRecipe(eq(1L), any(Recipe.class))).thenReturn(Optional.of(recipe));

        mockMvc.perform(multipart("/recipe/1/user@example.com")
                        .file(image)
                        .file(recipePart)
                        .with(request -> { request.setMethod("PUT"); return request; }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeName").value("Updated Tortilla"));
    }

    @Test
    void deleteRecipe_shouldReturnNoContent() throws Exception {
        when(recipeService.deleteRecipe(1L)).thenReturn(true);

        mockMvc.perform(delete("/recipe/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecipe_shouldReturnNotFound() throws Exception {
        when(recipeService.deleteRecipe(99L)).thenReturn(false);

        mockMvc.perform(delete("/recipe/99"))
                .andExpect(status().isNotFound());
    }
}