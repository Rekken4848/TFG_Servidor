package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import com.hmaresc.TFG_Servidor.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private User createSampleUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("123");
        user.setName("Test");
        user.setLastName("User");
        user.setCreationDate(LocalDate.of(2025, 7, 27));
        return user;
    }

    @Test
    void getAllUsers_shouldReturnOk() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(createSampleUser()));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    void getUserById_shouldReturnUser_whenExists() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(createSampleUser()));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getUserById_shouldReturnNotFound_whenMissing() throws Exception {
        when(userService.getUserById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByEmail_shouldReturnUser_whenExists() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(createSampleUser()));

        mockMvc.perform(get("/users/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUserByEmail_shouldReturnNotFound_whenMissing() throws Exception {
        when(userService.getUserByEmail("missing@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/email/missing@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserByUsername_shouldReturnUser_whenExists() throws Exception {
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(createSampleUser()));

        mockMvc.perform(get("/users/username/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getUserByUsername_shouldReturnNotFound_whenMissing() throws Exception {
        when(userService.getUserByUsername("nouser")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/username/nouser"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsersCreatedAfter_shouldReturnFilteredUsers() throws Exception {
        LocalDate date = LocalDate.of(2025, 1, 1);
        when(userService.getUsersCreatedAfter(date)).thenReturn(List.of(createSampleUser()));

        mockMvc.perform(get("/users/created-after/2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        User user = createSampleUser();
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser_whenExists() throws Exception {
        User user = createSampleUser();
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(Optional.of(user));

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void updateUser_shouldReturnNotFound_whenMissing() throws Exception {
        User user = createSampleUser();
        when(userService.updateUser(eq(99L), any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_shouldReturnNoContent_whenDeleted() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_shouldReturnNotFound_whenMissing() throws Exception {
        when(userService.deleteUser(99L)).thenReturn(false);

        mockMvc.perform(delete("/users/99"))
                .andExpect(status().isNotFound());
    }
}