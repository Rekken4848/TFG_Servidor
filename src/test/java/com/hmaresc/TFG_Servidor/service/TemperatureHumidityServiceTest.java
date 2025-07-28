package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.TemperatureHumidity;
import com.hmaresc.TFG_Servidor.repository.TemperatureHumidityRepository;
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
class TemperatureHumidityServiceTest {

    @Mock
    private TemperatureHumidityRepository temperatureHumidityRepository;

    @InjectMocks
    private TemperatureHumidityService temperatureHumidityService;

    private TemperatureHumidity data;

    @BeforeEach
    void setUp() {
        data = new TemperatureHumidity();
        data.setId(1L);
        data.setTemperature(22.5f);
        data.setHumidity(60.0f);
    }

    @Test
    void getAllTemperatureHumidity_shouldReturnList() {
        when(temperatureHumidityRepository.findAll()).thenReturn(List.of(data));

        List<TemperatureHumidity> result = temperatureHumidityService.getAllTemperatureHumidity();

        assertEquals(1, result.size());
        assertEquals(22.5f, result.get(0).getTemperature());
        assertEquals(60.0f, result.get(0).getHumidity());
    }

    @Test
    void getTemperatureHumidityById_shouldReturnData_whenExists() {
        when(temperatureHumidityRepository.findById(1L)).thenReturn(Optional.of(data));

        Optional<TemperatureHumidity> result = temperatureHumidityService.getTemperatureHumidityById(1L);

        assertTrue(result.isPresent());
        assertEquals(22.5f, result.get().getTemperature());
    }

    @Test
    void getTemperatureHumidityById_shouldReturnEmpty_whenNotFound() {
        when(temperatureHumidityRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<TemperatureHumidity> result = temperatureHumidityService.getTemperatureHumidityById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void getLatestData_shouldReturnLatest_whenExists() {
        when(temperatureHumidityRepository.findLatest()).thenReturn(Optional.of(data));

        Optional<TemperatureHumidity> result = temperatureHumidityService.getLatestData();

        assertTrue(result.isPresent());
        assertEquals(60.0f, result.get().getHumidity());
    }

    @Test
    void getLatestData_shouldReturnEmpty_whenNotFound() {
        when(temperatureHumidityRepository.findLatest()).thenReturn(Optional.empty());

        Optional<TemperatureHumidity> result = temperatureHumidityService.getLatestData();

        assertFalse(result.isPresent());
    }

    @Test
    void createTemperatureHumidity_shouldSaveAndReturnData() {
        when(temperatureHumidityRepository.save(data)).thenReturn(data);

        TemperatureHumidity result = temperatureHumidityService.createTemperatureHumidity(data);

        assertNotNull(result);
        assertEquals(22.5f, result.getTemperature());
        verify(temperatureHumidityRepository).save(data);
    }

    @Test
    void updateTemperatureHumidity_shouldUpdate_whenExists() {
        TemperatureHumidity updated = new TemperatureHumidity();
        updated.setTemperature(24.0f);
        updated.setHumidity(55.0f);

        when(temperatureHumidityRepository.findById(1L)).thenReturn(Optional.of(data));
        when(temperatureHumidityRepository.save(any(TemperatureHumidity.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<TemperatureHumidity> result = temperatureHumidityService.updateTemperatureHumidity(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(24.0f, result.get().getTemperature());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateTemperatureHumidity_shouldReturnEmpty_whenNotFound() {
        when(temperatureHumidityRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<TemperatureHumidity> result = temperatureHumidityService.updateTemperatureHumidity(99L, data);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteTemperatureHumidity_shouldDelete_whenExists() {
        when(temperatureHumidityRepository.existsById(1L)).thenReturn(true);

        boolean result = temperatureHumidityService.deleteTemperatureHumidity(1L);

        assertTrue(result);
        verify(temperatureHumidityRepository).deleteById(1L);
    }

    @Test
    void deleteTemperatureHumidity_shouldReturnFalse_whenNotExists() {
        when(temperatureHumidityRepository.existsById(99L)).thenReturn(false);

        boolean result = temperatureHumidityService.deleteTemperatureHumidity(99L);

        assertFalse(result);
        verify(temperatureHumidityRepository, never()).deleteById(anyLong());
    }
}