package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence;

import com.example.apicontrolcrypt.domain.model.User;
import com.example.apicontrolcrypt.domain.port.out.UserRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper.UserEntityMapper;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper mapper;

    @Override
    public User save(User user) {
        return mapper.toDomain(jpaUserRepository.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
