package com.example.apicontrolcrypt.infrastructure.security;

import com.example.apicontrolcrypt.domain.model.User;
import com.example.apicontrolcrypt.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_found_returnsUserDetails() {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .password("encoded-password")
                .roles(Set.of("ROLE_USER"))
                .active(Boolean.TRUE)
                .build();

        when(userRepositoryPort.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        UserDetails details = userDetailsService.loadUserByUsername("user@example.com");

        assertThat(details.getUsername()).isEqualTo("user@example.com");
        assertThat(details.getAuthorities()).extracting("authority")
                .containsExactlyInAnyOrder("ROLE_USER");
    }

    @Test
    void loadUserByUsername_notFound_throwsException() {
        when(userRepositoryPort.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("unknown@example.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void loadUserByUsername_inactiveUser_throwsException() {
        User user = User.builder()
                .email("inactive@example.com")
                .password("encoded-password")
                .roles(Set.of("ROLE_USER"))
                .active(Boolean.FALSE)
                .build();

        when(userRepositoryPort.findByEmail("inactive@example.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("inactive@example.com"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("inactive");
    }
}
