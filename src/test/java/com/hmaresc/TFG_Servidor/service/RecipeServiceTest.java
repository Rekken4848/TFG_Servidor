package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.RecipeIngredient;
import com.hmaresc.TFG_Servidor.model.RecipeInstruction;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    private User user;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setUser(user);
        recipe.setRecipeName("Test Recipe");
        recipe.setRecipeCategory("Dinner");
        recipe.setPrepTime(30);
        recipe.setServings(4);
        recipe.setIngredients(List.of(new RecipeIngredient()));
        recipe.setInstructions(List.of(new RecipeInstruction()));
    }

    @Test
    void getAllRecipes_shouldReturnRecipeList() {
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        List<Recipe> result = recipeService.getAllRecipes();

        assertEquals(1, result.size());
        assertEquals("Test Recipe", result.get(0).getRecipeName());
    }

    @Test
    void getRecipeById_shouldReturnRecipe_whenFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Optional<Recipe> result = recipeService.getRecipeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Recipe", result.get().getRecipeName());
    }

    @Test
    void getRecipeById_shouldReturnEmpty_whenNotFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Recipe> result = recipeService.getRecipeById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void getRecipeByUser_shouldReturnRecipesAndInitializeCollections() {
        List<Recipe> recipes = List.of(recipe);
        when(recipeRepository.findByUser(user)).thenReturn(Optional.of(recipes));

        Optional<List<Recipe>> result = recipeService.getRecipeByUser(user);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertNotNull(result.get().get(0).getIngredients());
        assertNotNull(result.get().get(0).getInstructions());
    }

    @Test
    void getRecipeByUser_shouldReturnEmpty_whenNoRecipes() {
        when(recipeRepository.findByUser(user)).thenReturn(Optional.empty());

        Optional<List<Recipe>> result = recipeService.getRecipeByUser(user);

        assertFalse(result.isPresent());
    }

    @Test
    void createRecipe_shouldSaveAndReturnRecipe() {
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe result = recipeService.createRecipe(recipe);

        assertNotNull(result);
        assertEquals("Test Recipe", result.getRecipeName());
        verify(recipeRepository).save(recipe);
    }

    @Test
    void updateRecipe_shouldUpdate_whenExists() {
        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setRecipeName("Updated Recipe");
        updatedRecipe.setUser(user);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Recipe> result = recipeService.updateRecipe(1L, updatedRecipe);

        assertTrue(result.isPresent());
        assertEquals("Updated Recipe", result.get().getRecipeName());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateRecipe_shouldReturnEmpty_whenNotFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Recipe> result = recipeService.updateRecipe(99L, recipe);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteRecipe_shouldDelete_whenExists() {
        when(recipeRepository.existsById(1L)).thenReturn(true);

        boolean result = recipeService.deleteRecipe(1L);

        assertTrue(result);
        verify(recipeRepository).deleteById(1L);
    }

    @Test
    void deleteRecipe_shouldReturnFalse_whenNotFound() {
        when(recipeRepository.existsById(99L)).thenReturn(false);

        boolean result = recipeService.deleteRecipe(99L);

        assertFalse(result);
    }
}