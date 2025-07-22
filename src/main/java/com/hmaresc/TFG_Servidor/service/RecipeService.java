package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.model.UserStats;
import com.hmaresc.TFG_Servidor.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    @Transactional
    public Optional<List<Recipe>> getRecipeByUser(User user) {
        //return recipeRepository.findByUser(user);
        Optional<List<Recipe>> recipes = recipeRepository.findByUser(user);

        recipes.ifPresent(list -> {
            for (Recipe recipe : list) {
                recipe.getIngredients().size();
                recipe.getInstructions().size();
            }
        });

        return recipes;
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<Recipe> updateRecipe(Long id, Recipe recipeDetails) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (!recipeOptional.isPresent()) {
            return Optional.empty();
        }
        recipeDetails.setId(id);
        Recipe updatedRecipe = recipeRepository.save(recipeDetails);
        return Optional.of(updatedRecipe);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            return false;
        }
        recipeRepository.deleteById(id);
        return true;
    }
}
