package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.TemperatureHumidity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemperatureHumidityRepository extends JpaRepository<TemperatureHumidity, Long> {
    @Query(value = "SELECT * FROM temperature_humidity ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<TemperatureHumidity> findLatest();
}
