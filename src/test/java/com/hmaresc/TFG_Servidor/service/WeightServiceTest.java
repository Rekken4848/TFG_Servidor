package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Weight;
import com.hmaresc.TFG_Servidor.repository.WeightRepository;
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
class WeightServiceTest {

    @Mock
    private WeightRepository weightRepository;

    @InjectMocks
    private WeightService weightService;

    private Weight weight;

    @BeforeEach
    void setUp() {
        weight = new Weight();
        weight.setId(1L);
        weight.setWeight(80.0f);
    }

    @Test
    void getAllWeights_shouldReturnList() {
        when(weightRepository.findAll()).thenReturn(List.of(weight));

        List<Weight> result = weightService.getAllWeights();

        assertEquals(1, result.size());
        assertEquals(80.0f, result.get(0).getWeight());
    }

    @Test
    void getWeightById_shouldReturnWeight_whenExists() {
        when(weightRepository.findById(1L)).thenReturn(Optional.of(weight));

        Optional<Weight> result = weightService.getWeightById(1L);

        assertTrue(result.isPresent());
        assertEquals(80.0f, result.get().getWeight());
    }

    @Test
    void getWeightById_shouldReturnEmpty_whenNotFound() {
        when(weightRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Weight> result = weightService.getWeightById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void getLatestData_shouldReturnLatestWeight_whenExists() {
        when(weightRepository.findLatest()).thenReturn(Optional.of(weight));

        Optional<Weight> result = weightService.getLatestData();

        assertTrue(result.isPresent());
        assertEquals(80.0f, result.get().getWeight());
    }

    @Test
    void getLatestData_shouldReturnEmpty_whenNotFound() {
        when(weightRepository.findLatest()).thenReturn(Optional.empty());

        Optional<Weight> result = weightService.getLatestData();

        assertFalse(result.isPresent());
    }

    @Test
    void createWeight_shouldSaveAndReturnWeight() {
        when(weightRepository.save(weight)).thenReturn(weight);

        Weight result = weightService.createWeight(weight);

        assertNotNull(result);
        assertEquals(80.0f, result.getWeight());
        verify(weightRepository).save(weight);
    }

    @Test
    void updateWeight_shouldUpdate_whenExists() {
        Weight updated = new Weight();
        updated.setWeight(85.5f);

        when(weightRepository.findById(1L)).thenReturn(Optional.of(weight));
        when(weightRepository.save(any(Weight.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Weight> result = weightService.updateWeight(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(85.5f, result.get().getWeight());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateWeight_shouldReturnEmpty_whenNotFound() {
        when(weightRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Weight> result = weightService.updateWeight(99L, weight);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteWeight_shouldDelete_whenExists() {
        when(weightRepository.existsById(1L)).thenReturn(true);

        boolean result = weightService.deleteWeight(1L);

        assertTrue(result);
        verify(weightRepository).deleteById(1L);
    }

    @Test
    void deleteWeight_shouldReturnFalse_whenNotExists() {
        when(weightRepository.existsById(99L)).thenReturn(false);

        boolean result = weightService.deleteWeight(99L);

        assertFalse(result);
        verify(weightRepository, never()).deleteById(anyLong());
    }
}