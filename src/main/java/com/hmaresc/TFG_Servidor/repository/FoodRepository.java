package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.Food;
import com.hmaresc.TFG_Servidor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByCode(String code);
    Optional<List<Food>> findByUser(User user);
}
