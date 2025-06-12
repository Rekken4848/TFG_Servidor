package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.UsersRecipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRecipesRepository extends JpaRepository<UsersRecipes, Long> {
}
