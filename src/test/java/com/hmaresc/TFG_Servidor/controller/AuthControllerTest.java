package com.hmaresc.TFG_Servidor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmaresc.TFG_Servidor.Security.JwtService;
import com.hmaresc.TFG_Servidor.config.TestSecurityConfig;
import com.hmaresc.TFG_Servidor.dto.LoginRequest;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.repository.UserRepository;
import com.hmaresc.TFG_Servidor.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void login_shouldReturnTokenAndUser() throws Exception {
        // Arrange
        String emailOrUsername = "test@example.com";
        String password = "123456";

        LoginRequest request = new LoginRequest();
        request.setCredentials(emailOrUsername);
        request.setPassword(password);

        User user = new User();
        user.setId(1L);
        user.setEmail(emailOrUsername);
        user.setUsername("testuser");
        user.setPassword(password);
        user.setName("Test");
        user.setLastName("User");

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of())
                .build();

        String token = "mocked.jwt.token";

        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail(emailOrUsername)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn(token);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.user.email").value(emailOrUsername));
    }

    @Test
    void getCurrentUser_shouldReturnUser() throws Exception {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setUsername("testuser");
        user.setPassword("123456");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Simular que ya hay un usuario autenticado en el contexto
        TestingAuthenticationToken auth =
                new TestingAuthenticationToken(email, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Act & Assert
        mockMvc.perform(get("/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }
}
