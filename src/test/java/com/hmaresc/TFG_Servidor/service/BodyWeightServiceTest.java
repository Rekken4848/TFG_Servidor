package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.BodyWeight;
import com.hmaresc.TFG_Servidor.repository.BodyWeightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BodyWeightServiceTest {

    @Mock
    private BodyWeightRepository bodyWeightRepository;

    @InjectMocks
    private BodyWeightService bodyWeightService;

    private BodyWeight bodyWeight;

    @BeforeEach
    void setUp() {
        bodyWeight = new BodyWeight();
        bodyWeight.setId(1L);
        bodyWeight.setBodyWeight(72.5f);
    }

    @Test
    void getAllBodyWeights_shouldReturnList() {
        when(bodyWeightRepository.findAll()).thenReturn(List.of(bodyWeight));

        List<BodyWeight> result = bodyWeightService.getAllBodyWeights();

        assertEquals(1, result.size());
        assertEquals(72.5f, result.get(0).getBodyWeight());
    }

    @Test
    void getBodyWeightById_shouldReturnBodyWeight_whenFound() {
        when(bodyWeightRepository.findById(1L)).thenReturn(Optional.of(bodyWeight));

        Optional<BodyWeight> result = bodyWeightService.getBodyWeightById(1L);

        assertTrue(result.isPresent());
        assertEquals(72.5f, result.get().getBodyWeight());
    }

    @Test
    void getBodyWeightById_shouldReturnEmpty_whenNotFound() {
        when(bodyWeightRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<BodyWeight> result = bodyWeightService.getBodyWeightById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void getLatestData_shouldReturnLatestBodyWeight_whenExists() {
        when(bodyWeightRepository.findLatest()).thenReturn(Optional.of(bodyWeight));

        Optional<BodyWeight> result = bodyWeightService.getLatestData();

        assertTrue(result.isPresent());
        assertEquals(72.5f, result.get().getBodyWeight());
    }

    @Test
    void getLatestData_shouldReturnEmpty_whenNotFound() {
        when(bodyWeightRepository.findLatest()).thenReturn(Optional.empty());

        Optional<BodyWeight> result = bodyWeightService.getLatestData();

        assertFalse(result.isPresent());
    }

    @Test
    void createBodyWeight_shouldSaveAndReturnBodyWeight() {
        when(bodyWeightRepository.save(bodyWeight)).thenReturn(bodyWeight);

        BodyWeight result = bodyWeightService.createBodyWeight(bodyWeight);

        assertNotNull(result);
        assertEquals(72.5f, result.getBodyWeight());
        verify(bodyWeightRepository).save(bodyWeight);
    }

    @Test
    void updateBodyWeight_shouldUpdate_whenExists() {
        BodyWeight updated = new BodyWeight();
        updated.setBodyWeight(75.0f);

        when(bodyWeightRepository.findById(1L)).thenReturn(Optional.of(bodyWeight));
        when(bodyWeightRepository.save(any(BodyWeight.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<BodyWeight> result = bodyWeightService.updateBodyWeight(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(75.0f, result.get().getBodyWeight());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateBodyWeight_shouldReturnEmpty_whenNotFound() {
        when(bodyWeightRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<BodyWeight> result = bodyWeightService.updateBodyWeight(99L, bodyWeight);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteBodyWeight_shouldDelete_whenExists() {
        when(bodyWeightRepository.existsById(1L)).thenReturn(true);

        boolean result = bodyWeightService.deleteBodyWeight(1L);

        assertTrue(result);
        verify(bodyWeightRepository).deleteById(1L);
    }

    @Test
    void deleteBodyWeight_shouldReturnFalse_whenNotExists() {
        when(bodyWeightRepository.existsById(99L)).thenReturn(false);

        boolean result = bodyWeightService.deleteBodyWeight(99L);

        assertFalse(result);
        verify(bodyWeightRepository, never()).deleteById(anyLong());
    }
}