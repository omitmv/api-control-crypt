package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.User;
import com.example.apicontrolcrypt.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .password("password123")
                .roles(Set.of("ROLE_USER"))
                .active(Boolean.TRUE)
                .build();
    }

    @Test
    void createUser_success() {
        when(userRepositoryPort.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
        when(userRepositoryPort.save(any(User.class))).thenReturn(user);

        User created = userService.createUser(user);

        assertThat(created).isNotNull();
        assertThat(created.getEmail()).isEqualTo("test@example.com");
        verify(userRepositoryPort).save(any(User.class));
    }

    @Test
    void createUser_duplicateEmail_throwsException() {
        when(userRepositoryPort.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email already in use");
    }

    @Test
    void getUserById_found() {
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1L);

        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    void getUserById_notFound_throwsException() {
        when(userRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void getAllUsers_returnsList() {
        when(userRepositoryPort.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(1);
    }

    @Test
    void deleteUser_success() {
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepositoryPort).deleteById(1L);
    }

    @Test
    void updateUser_updatesName() {
        User update = User.builder().name("New Name").build();
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        when(userRepositoryPort.save(any(User.class))).thenReturn(user);

        userService.updateUser(1L, update);

        verify(userRepositoryPort).save(any(User.class));
    }
}
