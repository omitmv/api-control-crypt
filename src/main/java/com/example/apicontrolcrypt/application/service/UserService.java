package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.User;
import com.example.apicontrolcrypt.domain.port.in.UserUseCase;
import com.example.apicontrolcrypt.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        log.info("Criando usuário: email={}", user.getEmail());
        if (userRepositoryPort.existsByEmail(user.getEmail())) {
            log.warn("Email já em uso: email={}", user.getEmail());
            throw new IllegalArgumentException("Email already in use: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("ROLE_USER"));
        }
        user.setActive(Boolean.TRUE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User saved = userRepositoryPort.save(user);
        log.info("Usuário criado com sucesso: id={}, email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    @Override
    public User getUserById(Long id) {
        log.debug("Buscando usuário: id={}", id);
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado: id={}", id);
                    return new IllegalArgumentException("User not found with id: " + id);
                });
    }

    @Override
    public User getUserByEmail(String email) {
        log.debug("Buscando usuário: email={}", email);
        return userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado: email={}", email);
                    return new IllegalArgumentException("User not found with email: " + email);
                });
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Listando todos os usuários");
        return userRepositoryPort.findAll();
    }

    @Override
    public User updateUser(Long id, User userUpdate) {
        log.info("Atualizando usuário: id={}", id);
        User existing = getUserById(id);

        if (userUpdate.getName() != null) {
            existing.setName(userUpdate.getName());
        }
        if (userUpdate.getEmail() != null && !userUpdate.getEmail().equals(existing.getEmail())) {
            if (userRepositoryPort.existsByEmail(userUpdate.getEmail())) {
                log.warn("Email já em uso ao atualizar usuário: email={}", userUpdate.getEmail());
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
        User updated = userRepositoryPort.save(existing);
        log.info("Usuário atualizado com sucesso: id={}", updated.getId());
        return updated;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deletando usuário: id={}", id);
        getUserById(id);
        userRepositoryPort.deleteById(id);
        log.info("Usuário deletado com sucesso: id={}", id);
    }
}
