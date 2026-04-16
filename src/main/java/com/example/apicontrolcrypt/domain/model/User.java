package com.example.apicontrolcrypt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isActive() {
        return Boolean.TRUE.equals(active);
    }
}
