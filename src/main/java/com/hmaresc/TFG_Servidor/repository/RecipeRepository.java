package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<List<Recipe>> findByUser(User user);
}
