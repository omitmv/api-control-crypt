package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.User;
import com.example.apicontrolcrypt.domain.port.in.UserUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.CreateUserRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper.UserRequestMapper;
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

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class, JwtUtil.class, UserRequestMapper.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserUseCase userUseCase;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void createUser_validRequest_returns201() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .roles(Set.of("ROLE_USER"))
                .build();

        User savedUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .roles(Set.of("ROLE_USER"))
                .active(Boolean.TRUE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userUseCase.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createUser_invalidRequest_returns400() throws Exception {
        CreateUserRequest request = CreateUserRequest.builder()
                .name("")
                .email("not-an-email")
                .password("short")
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
