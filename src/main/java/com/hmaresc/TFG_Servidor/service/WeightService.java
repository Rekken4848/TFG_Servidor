package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.TemperatureHumidity;
import com.hmaresc.TFG_Servidor.model.Weight;
import com.hmaresc.TFG_Servidor.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeightService {

    @Autowired
    private WeightRepository weightRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<Weight> getAllWeights() {
        return weightRepository.findAll();
    }

    public Optional<Weight> getWeightById(Long id) {
        return weightRepository.findById(id);
    }

    public Optional<Weight> getLatestData() {
        return weightRepository.findLatest();
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public Weight createWeight(Weight weight) {
        return weightRepository.save(weight);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<Weight> updateWeight(Long id, Weight weightDetails) {
        Optional<Weight> weightOptional = weightRepository.findById(id);
        if (!weightOptional.isPresent()) {
            return Optional.empty();
        }
        weightDetails.setId(id);
        Weight updatedWeight = weightRepository.save(weightDetails);
        return Optional.of(updatedWeight);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteWeight(Long id) {
        if (!weightRepository.existsById(id)) {
            return false;
        }
        weightRepository.deleteById(id);
        return true;
    }
}
