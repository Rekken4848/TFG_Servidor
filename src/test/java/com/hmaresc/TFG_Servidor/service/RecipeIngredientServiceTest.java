package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.RecipeIngredient;
import com.hmaresc.TFG_Servidor.repository.RecipeIngredientRepository;
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
class RecipeIngredientServiceTest {

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @InjectMocks
    private RecipeIngredientService recipeIngredientService;

    private RecipeIngredient recipeIngredient;

    @BeforeEach
    void setUp() {
        recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(1L);
        recipeIngredient.setName("Tomato");
        recipeIngredient.setQuantity("2");
        recipeIngredient.setUnit("pcs");

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipeIngredient.setRecipe(recipe);
    }

    @Test
    void getAllRecipesIngredient_shouldReturnList() {
        when(recipeIngredientRepository.findAll()).thenReturn(List.of(recipeIngredient));

        List<RecipeIngredient> result = recipeIngredientService.getAllRecipesIngredient();

        assertEquals(1, result.size());
        assertEquals("Tomato", result.get(0).getName());
    }

    @Test
    void getRecipeIngredientById_shouldReturnIngredient_whenFound() {
        when(recipeIngredientRepository.findById(1L)).thenReturn(Optional.of(recipeIngredient));

        Optional<RecipeIngredient> result = recipeIngredientService.getRecipeIngredientById(1L);

        assertTrue(result.isPresent());
        assertEquals("Tomato", result.get().getName());
    }

    @Test
    void getRecipeIngredientById_shouldReturnEmpty_whenNotFound() {
        when(recipeIngredientRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<RecipeIngredient> result = recipeIngredientService.getRecipeIngredientById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void createRecipeIngredient_shouldSaveAndReturnIngredient() {
        when(recipeIngredientRepository.save(recipeIngredient)).thenReturn(recipeIngredient);

        RecipeIngredient result = recipeIngredientService.createRecipeIngredient(recipeIngredient);

        assertNotNull(result);
        assertEquals("Tomato", result.getName());
        verify(recipeIngredientRepository).save(recipeIngredient);
    }

    @Test
    void updateRecipeIngredient_shouldUpdate_whenExists() {
        RecipeIngredient updated = new RecipeIngredient();
        updated.setName("Onion");
        updated.setQuantity("1");
        updated.setUnit("pcs");
        updated.setRecipe(recipeIngredient.getRecipe());

        when(recipeIngredientRepository.findById(1L)).thenReturn(Optional.of(recipeIngredient));
        when(recipeIngredientRepository.save(any(RecipeIngredient.class))).thenAnswer(i -> i.getArgument(0));

        Optional<RecipeIngredient> result = recipeIngredientService.updateRecipeIngredient(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("Onion", result.get().getName());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateRecipeIngredient_shouldReturnEmpty_whenNotFound() {
        when(recipeIngredientRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<RecipeIngredient> result = recipeIngredientService.updateRecipeIngredient(99L, recipeIngredient);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteRecipeIngredient_shouldDelete_whenExists() {
        when(recipeIngredientRepository.existsById(1L)).thenReturn(true);

        boolean result = recipeIngredientService.deleteRecipeIngredient(1L);

        assertTrue(result);
        verify(recipeIngredientRepository).deleteById(1L);
    }

    @Test
    void deleteRecipeIngredient_shouldReturnFalse_whenNotExists() {
        when(recipeIngredientRepository.existsById(99L)).thenReturn(false);

        boolean result = recipeIngredientService.deleteRecipeIngredient(99L);

        assertFalse(result);
        verify(recipeIngredientRepository, never()).deleteById(anyLong());
    }
}