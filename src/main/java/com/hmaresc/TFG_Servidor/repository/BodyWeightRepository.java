package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.BodyWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyWeightRepository extends JpaRepository<BodyWeight, Long> {
    @Query(value = "SELECT * FROM body_weight ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<BodyWeight> findLatest();
}
