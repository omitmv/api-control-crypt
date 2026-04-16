package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.AuthCredentials;
import com.example.apicontrolcrypt.domain.model.AuthToken;
import com.example.apicontrolcrypt.domain.port.in.AuthUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.AuthRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthCredentials credentials = new AuthCredentials(request.getEmail(), request.getPassword());
        AuthToken token = authUseCase.authenticate(credentials);
        AuthResponse response = AuthResponse.builder()
                .token(token.getToken())
                .tokenType(token.getTokenType())
                .expiresIn(token.getExpiresIn())
                .email(token.getEmail())
                .build();
        return ResponseEntity.ok(response);
    }
}
