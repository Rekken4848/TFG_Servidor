package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.TemperatureHumidity;
import com.hmaresc.TFG_Servidor.service.TemperatureHumidityService;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TemperatureHumidityController.class)
@Import(TestSecurityConfig.class)
class TemperatureHumidityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private TemperatureHumidityService temperatureHumidityService;

    @Autowired
    private ObjectMapper objectMapper;

    private TemperatureHumidity sample;

    @BeforeEach
    void setUp() {
        sample = new TemperatureHumidity();
        sample.setId(1L);
        sample.setTemperature(23.5f);
        sample.setHumidity(60.0f);
    }

    @Test
    void getAllTemperatureHumidity_shouldReturnList() throws Exception {
        when(temperatureHumidityService.getAllTemperatureHumidity()).thenReturn(List.of(sample));

        mockMvc.perform(get("/temhum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].temperature").value(23.5))
                .andExpect(jsonPath("$[0].humidity").value(60.0));
    }

    @Test
    void getTemperatureHumidityById_shouldReturnOne() throws Exception {
        when(temperatureHumidityService.getTemperatureHumidityById(1L)).thenReturn(Optional.of(sample));

        mockMvc.perform(get("/temhum/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(23.5))
                .andExpect(jsonPath("$.humidity").value(60.0));
    }

    @Test
    void getTemperatureHumidityById_shouldReturnNotFound() throws Exception {
        when(temperatureHumidityService.getTemperatureHumidityById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/temhum/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLatestTemperatureHumidity_shouldReturnLast() throws Exception {
        when(temperatureHumidityService.getLatestData()).thenReturn(Optional.of(sample));

        mockMvc.perform(get("/temhum/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(23.5))
                .andExpect(jsonPath("$.humidity").value(60.0));
    }

    @Test
    void getLatestTemperatureHumidity_shouldReturnNotFound() throws Exception {
        when(temperatureHumidityService.getLatestData()).thenReturn(Optional.empty());

        mockMvc.perform(get("/temhum/latest"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTemperatureHumidity_shouldReturnCreated() throws Exception {
        when(temperatureHumidityService.createTemperatureHumidity(any(TemperatureHumidity.class))).thenReturn(sample);

        mockMvc.perform(post("/temhum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(23.5))
                .andExpect(jsonPath("$.humidity").value(60.0));
    }

    @Test
    void updateTemperatureHumidity_shouldReturnUpdated() throws Exception {
        sample.setTemperature(25.0f);
        sample.setHumidity(55.0f);
        when(temperatureHumidityService.updateTemperatureHumidity(eq(1L), any(TemperatureHumidity.class)))
                .thenReturn(Optional.of(sample));

        mockMvc.perform(put("/temhum/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(25.0))
                .andExpect(jsonPath("$.humidity").value(55.0));
    }

    @Test
    void updateTemperatureHumidity_shouldReturnNotFound() throws Exception {
        when(temperatureHumidityService.updateTemperatureHumidity(eq(999L), any(TemperatureHumidity.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/temhum/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTemperatureHumidity_shouldReturnNoContent() throws Exception {
        when(temperatureHumidityService.deleteTemperatureHumidity(1L)).thenReturn(true);

        mockMvc.perform(delete("/temhum/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTemperatureHumidity_shouldReturnNotFound() throws Exception {
        when(temperatureHumidityService.deleteTemperatureHumidity(999L)).thenReturn(false);

        mockMvc.perform(delete("/temhum/999"))
                .andExpect(status().isNotFound());
    }
}
