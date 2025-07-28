package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.BodyWeight;
import com.hmaresc.TFG_Servidor.service.BodyWeightService;
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

@WebMvcTest(BodyWeightController.class)
@Import(TestSecurityConfig.class)
class BodyWeightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private BodyWeightService bodyWeightService;

    @Autowired
    private ObjectMapper objectMapper;

    private BodyWeight sample;

    @BeforeEach
    void setUp() {
        sample = new BodyWeight();
        sample.setId(1L);
        sample.setBodyWeight(72.5f);
    }

    @Test
    void getAllBodyWeights_shouldReturnList() throws Exception {
        when(bodyWeightService.getAllBodyWeights()).thenReturn(List.of(sample));

        mockMvc.perform(get("/bodyweight"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].body_weight").value(72.5));
    }

    @Test
    void getBodyWeightById_shouldReturnOne() throws Exception {
        when(bodyWeightService.getBodyWeightById(1L)).thenReturn(Optional.of(sample));

        mockMvc.perform(get("/bodyweight/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body_weight").value(72.5));
    }

    @Test
    void getBodyWeightById_shouldReturnNotFound() throws Exception {
        when(bodyWeightService.getBodyWeightById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bodyweight/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLatestBodyWeight_shouldReturnLast() throws Exception {
        when(bodyWeightService.getLatestData()).thenReturn(Optional.of(sample));

        mockMvc.perform(get("/bodyweight/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body_weight").value(72.5));
    }

    @Test
    void getLatestBodyWeight_shouldReturnNotFound() throws Exception {
        when(bodyWeightService.getLatestData()).thenReturn(Optional.empty());

        mockMvc.perform(get("/bodyweight/latest"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBodyWeight_shouldReturnCreated() throws Exception {
        when(bodyWeightService.createBodyWeight(any(BodyWeight.class))).thenReturn(sample);

        mockMvc.perform(post("/bodyweight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body_weight").value(72.5));
    }

    @Test
    void updateBodyWeight_shouldReturnUpdated() throws Exception {
        sample.setBodyWeight(74.0f);
        when(bodyWeightService.updateBodyWeight(eq(1L), any(BodyWeight.class))).thenReturn(Optional.of(sample));

        mockMvc.perform(put("/bodyweight/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body_weight").value(74.0));
    }

    @Test
    void updateBodyWeight_shouldReturnNotFound() throws Exception {
        when(bodyWeightService.updateBodyWeight(eq(999L), any(BodyWeight.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/bodyweight/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBodyWeight_shouldReturnNoContent() throws Exception {
        when(bodyWeightService.deleteBodyWeight(1L)).thenReturn(true);

        mockMvc.perform(delete("/bodyweight/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBodyWeight_shouldReturnNotFound() throws Exception {
        when(bodyWeightService.deleteBodyWeight(999L)).thenReturn(false);

        mockMvc.perform(delete("/bodyweight/999"))
                .andExpect(status().isNotFound());
    }
}
