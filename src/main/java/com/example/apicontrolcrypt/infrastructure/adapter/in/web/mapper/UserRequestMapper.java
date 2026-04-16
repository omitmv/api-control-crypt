package com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper;

import com.example.apicontrolcrypt.domain.model.User;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.CreateUserRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.UpdateUserRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {

    public User toDomain(CreateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(request.getRoles())
                .build();
    }

    public User toDomain(UpdateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(request.getRoles())
                .active(request.getActive())
                .build();
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
