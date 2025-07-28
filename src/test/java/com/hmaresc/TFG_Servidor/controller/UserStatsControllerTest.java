package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.dto.UserWithStatsDTO;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.model.UserStats;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import com.hmaresc.TFG_Servidor.service.UserService;
import com.hmaresc.TFG_Servidor.service.UserStatsService;
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

@WebMvcTest(UserStatsController.class)
@Import(TestSecurityConfig.class)
class UserStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserStatsService userStatsService;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final User user = new User();
    private final UserStats stats = new UserStats();

    @BeforeEach
    void setUp() {
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setName("Test");
        user.setLastName("User");

        stats.setId(1L);
        stats.setAge(30);
        stats.setHeight(175f);
        stats.setWeight(70f);
        stats.setGender("Male");
        stats.setActivityLevel("Medium");
        stats.setUser(user);
    }

    @Test
    void getAllUserStats_shouldReturnOk() throws Exception {
        when(userStatsService.getAllUserStats()).thenReturn(List.of(stats));

        mockMvc.perform(get("/userstats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(30));
    }

    @Test
    void getUserStatsById_shouldReturnUserStats() throws Exception {
        when(userStatsService.getUserStatsById(1L)).thenReturn(Optional.of(stats));

        mockMvc.perform(get("/userstats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.height").value(175));
    }

    @Test
    void getUserAndStatsByEmail_shouldReturnDTO() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userStatsService.getUserStatsByUser(user)).thenReturn(Optional.of(stats));

        mockMvc.perform(get("/userstats/user/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.userStats.age").value(30));
    }

    @Test
    void createUserStats_shouldReturnCreatedStats() throws Exception {
        stats.setUser(null); // as in input
        when(userStatsService.createUserStats(any(UserStats.class))).thenReturn(stats);

        mockMvc.perform(post("/userstats/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stats)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void updateUserStats_shouldReturnUpdatedStats() throws Exception {
        when(userStatsService.updateUserStats(eq(1L), any(UserStats.class))).thenReturn(Optional.of(stats));

        mockMvc.perform(put("/userstats/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stats)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").value(70));
    }

    @Test
    void uploadAvatar_shouldReturnUpdatedStats() throws Exception {
        byte[] imageBytes = "fake-image".getBytes();
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", imageBytes);
        MockMultipartFile userStatsPart = new MockMultipartFile(
                "userStats", "",
                "application/json",
                objectMapper.writeValueAsBytes(stats)
        );

        when(userStatsService.updateUserStats(eq(stats.getId()), any(UserStats.class))).thenReturn(Optional.of(stats));

        mockMvc.perform(multipart("/userstats/upload-avatar/1")
                        .file(image)
                        .file(userStatsPart)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void updateUserAndStats_shouldReturnUpdatedDTO() throws Exception {
        UserWithStatsDTO dto = new UserWithStatsDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setUserStats(stats);

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(userStatsService.updateUserStats(eq(stats.getId()), any(UserStats.class))).thenReturn(Optional.of(stats));

        mockMvc.perform(put("/userstats/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.userStats.gender").value("Male"));
    }

    @Test
    void deleteUserStats_shouldReturnNoContent() throws Exception {
        when(userStatsService.deleteUserStats(1L)).thenReturn(true);

        mockMvc.perform(delete("/userstats/1"))
                .andExpect(status().isNoContent());
    }
}