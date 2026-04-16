package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.AuthCredentials;
import com.example.apicontrolcrypt.domain.model.AuthToken;
import com.example.apicontrolcrypt.domain.port.in.AuthUseCase;
import com.example.apicontrolcrypt.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthToken authenticate(AuthCredentials credentials) {
        log.info("Autenticando usuário: email={}", credentials.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        long expiration = jwtUtil.getExpirationMs();

        log.info("Usuário autenticado com sucesso: email={}", userDetails.getUsername());
        return AuthToken.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(expiration)
                .email(userDetails.getUsername())
                .build();
    }
}
