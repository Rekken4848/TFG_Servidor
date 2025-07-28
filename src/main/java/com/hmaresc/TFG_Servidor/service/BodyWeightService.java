package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.BodyWeight;
import com.hmaresc.TFG_Servidor.model.Weight;
import com.hmaresc.TFG_Servidor.repository.BodyWeightRepository;
import com.hmaresc.TFG_Servidor.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodyWeightService {

    @Autowired
    private BodyWeightRepository bodyWeightRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<BodyWeight> getAllBodyWeights() {
        return bodyWeightRepository.findAll();
    }

    public Optional<BodyWeight> getBodyWeightById(Long id) {
        return bodyWeightRepository.findById(id);
    }

    public Optional<BodyWeight> getLatestData() {
        return bodyWeightRepository.findLatest();
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public BodyWeight createBodyWeight(BodyWeight bodyWeight) {
        return bodyWeightRepository.save(bodyWeight);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<BodyWeight> updateBodyWeight(Long id, BodyWeight bodyWeightDetails) {
        Optional<BodyWeight> bodyWeightOptional = bodyWeightRepository.findById(id);
        if (!bodyWeightOptional.isPresent()) {
            return Optional.empty();
        }
        bodyWeightDetails.setId(id);
        BodyWeight updatedBodyWeight = bodyWeightRepository.save(bodyWeightDetails);
        return Optional.of(updatedBodyWeight);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteBodyWeight(Long id) {
        if (!bodyWeightRepository.existsById(id)) {
            return false;
        }
        bodyWeightRepository.deleteById(id);
        return true;
    }
}
