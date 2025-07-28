package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.Food;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.service.FoodService;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import com.hmaresc.TFG_Servidor.service.UserService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodController.class)
@Import(TestSecurityConfig.class)
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private FoodService foodService;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private final User user = new User();
    {
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setUsername("user");
    }

    private final Food food = new Food();
    {
        food.setId(1L);
        food.setCode("ABC123");
        food.setName("Apple");
        food.setUser(user);
    }

    @Test
    void getAllFoods_shouldReturnOk() throws Exception {
        when(foodService.getAllFoods()).thenReturn(List.of(food));

        mockMvc.perform(get("/food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"));
    }

    @Test
    void getFoodById_shouldReturnFood() throws Exception {
        when(foodService.getFoodById(1L)).thenReturn(Optional.of(food));

        mockMvc.perform(get("/food/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void getFoodByCode_shouldReturnFood() throws Exception {
        when(foodService.findByCode("ABC123")).thenReturn(Optional.of(food));

        mockMvc.perform(get("/food/code/ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    void getFoodByEmail_shouldReturnFoods() throws Exception {
        when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(foodService.getFoodByUser(user)).thenReturn(Optional.of(List.of(food)));

        mockMvc.perform(get("/food/myfoods/user@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("ABC123"));
    }

    @Test
    void createFood_shouldReturnCreatedFood() throws Exception {
        when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(foodService.createFood(any(Food.class))).thenReturn(food);

        mockMvc.perform(post("/food/user@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(food)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void updateFood_shouldReturnUpdatedFood() throws Exception {
        when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(foodService.updateFood(any(Long.class), any(Food.class))).thenReturn(Optional.of(food));

        mockMvc.perform(put("/food/1/user@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(food)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    void deleteFood_shouldReturnNoContent() throws Exception {
        when(foodService.deleteFood(1L)).thenReturn(true);

        mockMvc.perform(delete("/food/1"))
                .andExpect(status().isNoContent());
    }
}
