package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.Weight;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import com.hmaresc.TFG_Servidor.service.WeightService;
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

@WebMvcTest(WeightController.class)
@Import(TestSecurityConfig.class)
class WeightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private WeightService weightService;

    @Autowired
    private ObjectMapper objectMapper;

    private Weight sample;

    @BeforeEach
    void setUp() {
        sample = new Weight();
        sample.setId(1L);
        sample.setWeight(80.0f);
    }

    @Test
    void getAllWeights_shouldReturnList() throws Exception {
        when(weightService.getAllWeights()).thenReturn(List.of(sample));

        mockMvc.perform(get("/weight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].weight").value(80.0));
    }

    @Test
    void getWeightById_shouldReturnOne() throws Exception {
        when(weightService.getWeightById(1L)).thenReturn(Optional.of(sample));

        mockMvc.perform(get("/weight/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").value(80.0));
    }

    @Test
    void getWeightById_shouldReturnNotFound() throws Exception {
        when(weightService.getWeightById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/weight/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLatestWeight_shouldReturnLast() throws Exception {
        when(weightService.getLatestData()).thenReturn(Optional.of(sample));

        mockMvc.perform(get("/weight/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").value(80.0));
    }

    @Test
    void getLatestWeight_shouldReturnNotFound() throws Exception {
        when(weightService.getLatestData()).thenReturn(Optional.empty());

        mockMvc.perform(get("/weight/latest"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createWeight_shouldReturnCreated() throws Exception {
        when(weightService.createWeight(any(Weight.class))).thenReturn(sample);

        mockMvc.perform(post("/weight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").value(80.0));
    }

    @Test
    void updateWeight_shouldReturnUpdated() throws Exception {
        sample.setWeight(82.0f);
        when(weightService.updateWeight(eq(1L), any(Weight.class))).thenReturn(Optional.of(sample));

        mockMvc.perform(put("/weight/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").value(82.0));
    }

    @Test
    void updateWeight_shouldReturnNotFound() throws Exception {
        when(weightService.updateWeight(eq(999L), any(Weight.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/weight/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWeight_shouldReturnNoContent() throws Exception {
        when(weightService.deleteWeight(1L)).thenReturn(true);

        mockMvc.perform(delete("/weight/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteWeight_shouldReturnNotFound() throws Exception {
        when(weightService.deleteWeight(999L)).thenReturn(false);

        mockMvc.perform(delete("/weight/999"))
                .andExpect(status().isNotFound());
    }
}