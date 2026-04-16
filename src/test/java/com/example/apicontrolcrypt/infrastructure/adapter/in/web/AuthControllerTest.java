package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.AuthCredentials;
import com.example.apicontrolcrypt.domain.model.AuthToken;
import com.example.apicontrolcrypt.domain.port.in.AuthUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.AuthRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.AuthResponse;
import com.example.apicontrolcrypt.infrastructure.config.SecurityConfig;
import com.example.apicontrolcrypt.infrastructure.security.JwtAuthFilter;
import com.example.apicontrolcrypt.infrastructure.security.JwtUtil;
import com.example.apicontrolcrypt.infrastructure.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class, JwtUtil.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthUseCase authUseCase;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void login_validCredentials_returnsToken() throws Exception {
        AuthRequest request = new AuthRequest("user@example.com", "password123");
        AuthToken token = AuthToken.builder()
                .token("jwt-token")
                .tokenType("Bearer")
                .expiresIn(86400000L)
                .email("user@example.com")
                .build();

        when(authUseCase.authenticate(any(AuthCredentials.class))).thenReturn(token);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    void login_invalidRequest_returns400() throws Exception {
        AuthRequest request = new AuthRequest("", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
