package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.RecipeFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeFoodRepository extends JpaRepository<RecipeFood, Long> {
}
