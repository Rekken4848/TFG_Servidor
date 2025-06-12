package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Food;
import com.hmaresc.TFG_Servidor.model.RecipeFood;
import com.hmaresc.TFG_Servidor.repository.RecipeFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeFoodService {

    @Autowired
    private RecipeFoodRepository recipeFoodRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<RecipeFood> getAllRecipesFoods() {
        return recipeFoodRepository.findAll();
    }

    public Optional<RecipeFood> getRecipeFoodById(Long id) {
        return recipeFoodRepository.findById(id);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public RecipeFood createRecipeFood(RecipeFood recipeFood) {
        return recipeFoodRepository.save(recipeFood);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<RecipeFood> updateRecipeFood(Long id, RecipeFood recipeFoodDetails) {
        Optional<RecipeFood> recipeFoodOptional = recipeFoodRepository.findById(id);
        if (!recipeFoodOptional.isPresent()) {
            return Optional.empty();
        }
        recipeFoodDetails.setId(id);
        RecipeFood updatedRecipeFood = recipeFoodRepository.save(recipeFoodDetails);
        return Optional.of(updatedRecipeFood);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteRecipeFood(Long id) {
        if (!recipeFoodRepository.existsById(id)) {
            return false;
        }
        recipeFoodRepository.deleteById(id);
        return true;
    }
}
