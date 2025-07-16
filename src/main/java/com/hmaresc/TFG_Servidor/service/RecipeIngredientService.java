package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.RecipeIngredient;
import com.hmaresc.TFG_Servidor.repository.RecipeIngredientRepository;
import com.hmaresc.TFG_Servidor.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeIngredientService {

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<RecipeIngredient> getAllRecipesIngredient() {
        return recipeIngredientRepository.findAll();
    }

    public Optional<RecipeIngredient> getRecipeIngredientById(Long id) {
        return recipeIngredientRepository.findById(id);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public RecipeIngredient createRecipeIngredient(RecipeIngredient recipeIngredient) {
        return recipeIngredientRepository.save(recipeIngredient);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<RecipeIngredient> updateRecipeIngredient(Long id, RecipeIngredient recipeIngredientDetails) {
        Optional<RecipeIngredient> recipeIngredientOptional = recipeIngredientRepository.findById(id);
        if (!recipeIngredientOptional.isPresent()) {
            return Optional.empty();
        }
        recipeIngredientDetails.setId(id);
        RecipeIngredient updatedRecipeIngredient = recipeIngredientRepository.save(recipeIngredientDetails);
        return Optional.of(updatedRecipeIngredient);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteRecipeIngredient(Long id) {
        if (!recipeIngredientRepository.existsById(id)) {
            return false;
        }
        recipeIngredientRepository.deleteById(id);
        return true;
    }
}
