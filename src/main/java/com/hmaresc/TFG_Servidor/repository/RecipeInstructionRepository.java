package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.RecipeInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeInstructionRepository extends JpaRepository<RecipeInstruction, Long> {
}
