package com.hmaresc.TFG_Servidor.controller;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.RecipeIngredient;
import com.hmaresc.TFG_Servidor.model.RecipeInstruction;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.service.RecipeService;
import com.hmaresc.TFG_Servidor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    // .......................................................
    // .......................................................
    // .......................GET.............................
    // .......................................................
    // .......................................................
    // .......................................................
    // GET /recipe/
    // .......................................................
    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    // .......................................................
    // GET /recipe/<id>
    // .......................................................
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);

        if (recipe.isPresent()) {
            return ResponseEntity.ok(recipe.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // GET /recipe/myrecipes/<email>
    // .......................................................
    @GetMapping("/myrecipes/{email}")
    public ResponseEntity<List<Recipe>> getRecipeByEmail(@PathVariable String email) {
        Optional<User> userWithId = userService.getUserByEmail(email);
        Optional<List<Recipe>> recipes = recipeService.getRecipeByUser(userWithId.get());

        if (recipes.isPresent()) {
            return ResponseEntity.ok(recipes.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // .......................................................
    // .......................POST............................
    // .......................................................
    // .......................................................
    // .......................................................
    // POST /recipe/<email>
    // .......................................................
    @PostMapping(value = "/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRecipe(@PathVariable String email, @RequestPart("image") MultipartFile file, @RequestPart("recipe") Recipe recipe) {
        Optional<User> userWithId = userService.getUserByEmail(email);
        recipe.setUser(userWithId.get());
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }
        for (RecipeInstruction instruction : recipe.getInstructions()) {
            instruction.setRecipe(recipe);
        }
        if(!file.isEmpty()){
            try {
                recipe.setImageFile(file.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar imagen.");
            }
        }
        Recipe created = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(created);
    }

    // .......................................................
    // .......................................................
    // .....................UPDATE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // UPDATE /recipe/<id>/<email>
    // .......................................................
    @PutMapping(value = "/{id}/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @PathVariable String email, @RequestPart("image") MultipartFile file, @RequestPart("recipe") Recipe recipeDetails) {
        Optional<User> userWithId = userService.getUserByEmail(email);
        recipeDetails.setUser(userWithId.get());
        for (RecipeIngredient ingredient : recipeDetails.getIngredients()) {
            ingredient.setRecipe(recipeDetails);
        }
        for (RecipeInstruction instruction : recipeDetails.getInstructions()) {
            instruction.setRecipe(recipeDetails);
        }
        if(!file.isEmpty()){
            try {
                recipeDetails.setImageFile(file.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar imagen.");
            }
        }
        Optional<Recipe> updatedRecipe = recipeService.updateRecipe(id, recipeDetails);
        return updatedRecipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // .......................................................
    // .......................................................
    // .....................DELETE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // DELETE /recipe/<id>
    // .......................................................
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        boolean deleted = recipeService.deleteRecipe(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
