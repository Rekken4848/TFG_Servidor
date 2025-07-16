package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.RecipeIngredient;
import com.hmaresc.TFG_Servidor.model.RecipeInstruction;
import com.hmaresc.TFG_Servidor.repository.RecipeIngredientRepository;
import com.hmaresc.TFG_Servidor.repository.RecipeInstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeInstructionService {

    @Autowired
    private RecipeInstructionRepository recipeInstructionRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<RecipeInstruction> getAllRecipesInstruction() {
        return recipeInstructionRepository.findAll();
    }

    public Optional<RecipeInstruction> getRecipeInstructionById(Long id) {
        return recipeInstructionRepository.findById(id);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public RecipeInstruction createRecipeInstruction(RecipeInstruction recipeInstruction) {
        return recipeInstructionRepository.save(recipeInstruction);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<RecipeInstruction> updateRecipeInstruction(Long id, RecipeInstruction recipeInstructionDetails) {
        Optional<RecipeInstruction> recipeInstructionOptional = recipeInstructionRepository.findById(id);
        if (!recipeInstructionOptional.isPresent()) {
            return Optional.empty();
        }
        recipeInstructionDetails.setId(id);
        RecipeInstruction updatedRecipeInstruction = recipeInstructionRepository.save(recipeInstructionDetails);
        return Optional.of(updatedRecipeInstruction);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteRecipeInstruction(Long id) {
        if (!recipeInstructionRepository.existsById(id)) {
            return false;
        }
        recipeInstructionRepository.deleteById(id);
        return true;
    }
}
