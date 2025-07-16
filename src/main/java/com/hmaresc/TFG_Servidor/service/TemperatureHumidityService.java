package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.TemperatureHumidity;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.model.UserStats;
import com.hmaresc.TFG_Servidor.repository.TemperatureHumidityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemperatureHumidityService {

    @Autowired
    private TemperatureHumidityRepository temperatureHumidityRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<TemperatureHumidity> getAllTemperatureHumidity() {
        return temperatureHumidityRepository.findAll();
    }

    public Optional<TemperatureHumidity> getTemperatureHumidityById(Long id) {
        return temperatureHumidityRepository.findById(id);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public TemperatureHumidity createTemperatureHumidity(TemperatureHumidity temperatureHumidity) {
        return temperatureHumidityRepository.save(temperatureHumidity);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<TemperatureHumidity> updateTemperatureHumidity(Long id, TemperatureHumidity temperatureHumidityDetails) {
        Optional<TemperatureHumidity> temperatureHumidityOptional = temperatureHumidityRepository.findById(id);
        if (!temperatureHumidityOptional.isPresent()) {
            return Optional.empty();
        }
        temperatureHumidityDetails.setId(id);
        TemperatureHumidity updatedTemperatureHumidity = temperatureHumidityRepository.save(temperatureHumidityDetails);
        return Optional.of(updatedTemperatureHumidity);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteTemperatureHumidity(Long id) {
        if (!temperatureHumidityRepository.existsById(id)) {
            return false;
        }
        temperatureHumidityRepository.deleteById(id);
        return true;
    }
}
