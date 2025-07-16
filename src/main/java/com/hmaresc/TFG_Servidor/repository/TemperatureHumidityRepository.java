package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.TemperatureHumidity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureHumidityRepository extends JpaRepository<TemperatureHumidity, Long> {
}
