package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.AuthCredentials;
import com.example.apicontrolcrypt.domain.model.AuthToken;
import com.example.apicontrolcrypt.domain.port.in.AuthUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.AuthRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.info("Requisição de login: email={}", request.getEmail());
        AuthCredentials credentials = new AuthCredentials(request.getEmail(), request.getPassword());
        AuthToken token = authUseCase.authenticate(credentials);
        log.info("Login realizado com sucesso: email={}", request.getEmail());
        AuthResponse response = AuthResponse.builder()
                .token(token.getToken())
                .tokenType(token.getTokenType())
                .expiresIn(token.getExpiresIn())
                .email(token.getEmail())
                .build();
        return ResponseEntity.ok(response);
    }
}
