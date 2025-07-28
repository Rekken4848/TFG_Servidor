package com.hmaresc.TFG_Servidor.controller;

import com.hmaresc.TFG_Servidor.model.BodyWeight;
import com.hmaresc.TFG_Servidor.model.Weight;
import com.hmaresc.TFG_Servidor.service.BodyWeightService;
import com.hmaresc.TFG_Servidor.service.WeightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bodyweight")
public class BodyWeightController {

    @Autowired
    private BodyWeightService bodyWeightService;

    // .......................................................
    // .......................................................
    // .......................GET.............................
    // .......................................................
    // .......................................................
    // .......................................................
    // GET /bodyweight/
    // .......................................................
    @GetMapping
    public List<BodyWeight> getAllBodyWeights() {
        return bodyWeightService.getAllBodyWeights();
    }

    // .......................................................
    // GET /bodyweight/<id>
    // .......................................................
    @GetMapping("/{id}")
    public ResponseEntity<BodyWeight> getBodyWeightById(@PathVariable Long id) {
        Optional<BodyWeight> bodyWeight = bodyWeightService.getBodyWeightById(id);

        if (bodyWeight.isPresent()) {
            return ResponseEntity.ok(bodyWeight.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // GET /bodyweight/latest
    // .......................................................
    @GetMapping("/latest")
    public ResponseEntity<BodyWeight> getLatestBodyWeight() {
        Optional<BodyWeight> bodyWeight = bodyWeightService.getLatestData();

        if (bodyWeight.isPresent()) {
            return ResponseEntity.ok(bodyWeight.get());
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
    // POST /bodyweight/
    // .......................................................
    @PostMapping
    public ResponseEntity<BodyWeight> createBodyWeight(@Valid @RequestBody BodyWeight bodyWeight) {
        BodyWeight created = bodyWeightService.createBodyWeight(bodyWeight);
        return ResponseEntity.ok(created);
    }

    // .......................................................
    // .......................................................
    // .....................UPDATE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // UPDATE /bodyweight/<id>
    // .......................................................
    @PutMapping("/{id}")
    public ResponseEntity<BodyWeight> updateBodyWeight(@PathVariable Long id, @Valid @RequestBody BodyWeight bodyWeightDetails) {
        Optional<BodyWeight> updatedBodyWeight = bodyWeightService.updateBodyWeight(id, bodyWeightDetails);
        return updatedBodyWeight.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // .......................................................
    // .......................................................
    // .....................DELETE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // DELETE /bodyweight/<id>
    // .......................................................
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodyWeight(@PathVariable Long id) {
        boolean deleted = bodyWeightService.deleteBodyWeight(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
