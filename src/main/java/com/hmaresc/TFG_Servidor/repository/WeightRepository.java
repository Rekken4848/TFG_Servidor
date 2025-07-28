package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    @Query(value = "SELECT * FROM weight ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Weight> findLatest();
}
