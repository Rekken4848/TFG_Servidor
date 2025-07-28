package com.hmaresc.TFG_Servidor.controller;

import com.hmaresc.TFG_Servidor.model.TemperatureHumidity;
import com.hmaresc.TFG_Servidor.model.Weight;
import com.hmaresc.TFG_Servidor.service.WeightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/weight")
public class WeightController {

    @Autowired
    private WeightService weightService;

    // .......................................................
    // .......................................................
    // .......................GET.............................
    // .......................................................
    // .......................................................
    // .......................................................
    // GET /weight/
    // .......................................................
    @GetMapping
    public List<Weight> getAllWeights() {
        return weightService.getAllWeights();
    }

    // .......................................................
    // GET /weight/<id>
    // .......................................................
    @GetMapping("/{id}")
    public ResponseEntity<Weight> getWeightById(@PathVariable Long id) {
        Optional<Weight> weight = weightService.getWeightById(id);

        if (weight.isPresent()) {
            return ResponseEntity.ok(weight.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // GET /weight/latest
    // .......................................................
    @GetMapping("/latest")
    public ResponseEntity<Weight> getLatestWeight() {
        Optional<Weight> weight = weightService.getLatestData();

        if (weight.isPresent()) {
            return ResponseEntity.ok(weight.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // .......................................................
    // .......................POST............................
    // .......................................................
    // .......................................................
    // .......................................................
    // POST /weight/
    // .......................................................
    @PostMapping
    public ResponseEntity<Weight> createWeight(@Valid @RequestBody Weight weight) {
        Weight created = weightService.createWeight(weight);
        return ResponseEntity.ok(created);
    }

    // .......................................................
    // .......................................................
    // .....................UPDATE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // UPDATE /weight/<id>
    // .......................................................
    @PutMapping("/{id}")
    public ResponseEntity<Weight> updateWeight(@PathVariable Long id, @Valid @RequestBody Weight weightDetails) {
        Optional<Weight> updatedWeight = weightService.updateWeight(id, weightDetails);
        return updatedWeight.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // .......................................................
    // .......................................................
    // .....................DELETE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // DELETE /weight/<id>
    // .......................................................
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeight(@PathVariable Long id) {
        boolean deleted = weightService.deleteWeight(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
