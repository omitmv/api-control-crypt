package com.example.apicontrolcrypt.infrastructure.security;

import com.example.apicontrolcrypt.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {

    private final UserRepositoryPort userRepositoryPort;

    public boolean isOwner(Authentication authentication, Long userId) {
        String email = authentication.getName();
        return userRepositoryPort.findById(userId)
                .map(user -> user.getEmail().equals(email))
                .orElse(false);
    }
}
