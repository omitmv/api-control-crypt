package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.AuthCredentials;
import com.example.apicontrolcrypt.domain.model.AuthToken;
import com.example.apicontrolcrypt.domain.port.in.AuthUseCase;
import com.example.apicontrolcrypt.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthToken authenticate(AuthCredentials credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        long expiration = jwtUtil.getExpirationMs();

        return AuthToken.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(expiration)
                .email(userDetails.getUsername())
                .build();
    }
}
