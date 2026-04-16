package com.example.apicontrolcrypt.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret",
                "test-secret-key-for-testing-purposes-only-256bits");
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", 3600000L);

        userDetails = new User("test@example.com", "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void generateToken_returnsNonNull() {
        String token = jwtUtil.generateToken(userDetails);
        assertThat(token).isNotBlank();
    }

    @Test
    void extractUsername_returnsCorrectEmail() {
        String token = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.extractUsername(token);
        assertThat(username).isEqualTo("test@example.com");
    }

    @Test
    void validateToken_validToken_returnsTrue() {
        String token = jwtUtil.generateToken(userDetails);
        assertThat(jwtUtil.validateToken(token, userDetails)).isTrue();
    }

    @Test
    void validateToken_wrongUser_returnsFalse() {
        String token = jwtUtil.generateToken(userDetails);
        UserDetails other = new User("other@example.com", "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        assertThat(jwtUtil.validateToken(token, other)).isFalse();
    }
}
