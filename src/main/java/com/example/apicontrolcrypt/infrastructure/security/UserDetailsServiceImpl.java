package com.example.apicontrolcrypt.infrastructure.security;

import com.example.apicontrolcrypt.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepositoryPort.findByEmail(email)
                .map(user -> {
                    if (!user.isActive()) {
                        throw new UsernameNotFoundException("User is inactive: " + email);
                    }
                    return org.springframework.security.core.userdetails.User.builder()
                            .username(user.getEmail())
                            .password(user.getPassword())
                            .authorities(user.getRoles().stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList()))
                            .build();
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
