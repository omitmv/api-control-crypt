package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence;

import com.example.apicontrolcrypt.domain.model.Carteira;
import com.example.apicontrolcrypt.domain.port.out.CarteiraRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper.CarteiraEntityMapper;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository.JpaCarteiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CarteiraRepositoryAdapter implements CarteiraRepositoryPort {

    private final JpaCarteiraRepository jpaCarteiraRepository;
    private final CarteiraEntityMapper mapper;

    @Override
    public Carteira salvar(Carteira carteira) {
        return mapper.toDomain(jpaCarteiraRepository.save(mapper.toEntity(carteira)));
    }

    @Override
    public Optional<Carteira> buscarPorId(Integer id) {
        return jpaCarteiraRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Carteira> buscarTodas() {
        return jpaCarteiraRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Integer id) {
        jpaCarteiraRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Integer id) {
        return jpaCarteiraRepository.existsById(id);
    }

    @Override
    public boolean existePorDsCarteira(String dsCarteira) {
        return jpaCarteiraRepository.existsByDsCarteira(dsCarteira);
    }
}
