package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.User;
import com.example.apicontrolcrypt.domain.port.in.UserUseCase;
import com.example.apicontrolcrypt.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        if (userRepositoryPort.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("ROLE_USER"));
        }
        user.setActive(Boolean.TRUE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepositoryPort.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    public User updateUser(Long id, User userUpdate) {
        User existing = getUserById(id);

        if (userUpdate.getName() != null) {
            existing.setName(userUpdate.getName());
        }
        if (userUpdate.getEmail() != null && !userUpdate.getEmail().equals(existing.getEmail())) {
            if (userRepositoryPort.existsByEmail(userUpdate.getEmail())) {
                throw new IllegalArgumentException("Email already in use: " + userUpdate.getEmail());
            }
            existing.setEmail(userUpdate.getEmail());
        }
        if (userUpdate.getPassword() != null) {
            existing.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        if (userUpdate.getRoles() != null && !userUpdate.getRoles().isEmpty()) {
            existing.setRoles(userUpdate.getRoles());
        }
        if (userUpdate.getActive() != null) {
            existing.setActive(userUpdate.getActive());
        }
        existing.setUpdatedAt(LocalDateTime.now());
        return userRepositoryPort.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        getUserById(id);
        userRepositoryPort.deleteById(id);
    }
}
