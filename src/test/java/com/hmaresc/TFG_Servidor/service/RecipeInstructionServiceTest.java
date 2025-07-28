package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.RecipeInstruction;
import com.hmaresc.TFG_Servidor.repository.RecipeInstructionRepository;
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
class RecipeInstructionServiceTest {

    @Mock
    private RecipeInstructionRepository recipeInstructionRepository;

    @InjectMocks
    private RecipeInstructionService recipeInstructionService;

    private RecipeInstruction instruction;

    @BeforeEach
    void setUp() {
        instruction = new RecipeInstruction();
        instruction.setId(1L);
        instruction.setStepNumber(1);
        instruction.setText("Chop the onions finely.");
        instruction.setTime("5 min");

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        instruction.setRecipe(recipe);
    }

    @Test
    void getAllRecipesInstruction_shouldReturnList() {
        when(recipeInstructionRepository.findAll()).thenReturn(List.of(instruction));

        List<RecipeInstruction> result = recipeInstructionService.getAllRecipesInstruction();

        assertEquals(1, result.size());
        assertEquals("Chop the onions finely.", result.get(0).getText());
    }

    @Test
    void getRecipeInstructionById_shouldReturnInstruction_whenFound() {
        when(recipeInstructionRepository.findById(1L)).thenReturn(Optional.of(instruction));

        Optional<RecipeInstruction> result = recipeInstructionService.getRecipeInstructionById(1L);

        assertTrue(result.isPresent());
        assertEquals("Chop the onions finely.", result.get().getText());
    }

    @Test
    void getRecipeInstructionById_shouldReturnEmpty_whenNotFound() {
        when(recipeInstructionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<RecipeInstruction> result = recipeInstructionService.getRecipeInstructionById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void createRecipeInstruction_shouldSaveAndReturnInstruction() {
        when(recipeInstructionRepository.save(instruction)).thenReturn(instruction);

        RecipeInstruction result = recipeInstructionService.createRecipeInstruction(instruction);

        assertNotNull(result);
        assertEquals("Chop the onions finely.", result.getText());
        verify(recipeInstructionRepository).save(instruction);
    }

    @Test
    void updateRecipeInstruction_shouldUpdate_whenExists() {
        RecipeInstruction updated = new RecipeInstruction();
        updated.setStepNumber(2);
        updated.setText("Boil water.");
        updated.setTime("10 min");
        updated.setRecipe(instruction.getRecipe());

        when(recipeInstructionRepository.findById(1L)).thenReturn(Optional.of(instruction));
        when(recipeInstructionRepository.save(any(RecipeInstruction.class))).thenAnswer(i -> i.getArgument(0));

        Optional<RecipeInstruction> result = recipeInstructionService.updateRecipeInstruction(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("Boil water.", result.get().getText());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateRecipeInstruction_shouldReturnEmpty_whenNotFound() {
        when(recipeInstructionRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<RecipeInstruction> result = recipeInstructionService.updateRecipeInstruction(99L, instruction);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteRecipeInstruction_shouldDelete_whenExists() {
        when(recipeInstructionRepository.existsById(1L)).thenReturn(true);

        boolean result = recipeInstructionService.deleteRecipeInstruction(1L);

        assertTrue(result);
        verify(recipeInstructionRepository).deleteById(1L);
    }

    @Test
    void deleteRecipeInstruction_shouldReturnFalse_whenNotExists() {
        when(recipeInstructionRepository.existsById(99L)).thenReturn(false);

        boolean result = recipeInstructionService.deleteRecipeInstruction(99L);

        assertFalse(result);
        verify(recipeInstructionRepository, never()).deleteById(anyLong());
    }
}