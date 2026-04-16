package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence;

import com.example.apicontrolcrypt.domain.model.Moeda;
import com.example.apicontrolcrypt.domain.port.out.MoedaRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper.MoedaEntityMapper;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository.JpaMoedaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MoedaRepositoryAdapter implements MoedaRepositoryPort {

    private final JpaMoedaRepository jpaMoedaRepository;
    private final MoedaEntityMapper mapper;

    @Override
    public Moeda salvar(Moeda moeda) {
        return mapper.toDomain(jpaMoedaRepository.save(mapper.toEntity(moeda)));
    }

    @Override
    public Optional<Moeda> buscarPorId(Integer id) {
        return jpaMoedaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Moeda> buscarTodas() {
        return jpaMoedaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Moeda> buscarPorIdCarteira(Integer idCarteira) {
        return jpaMoedaRepository.findByCarteira_IdCarteira(idCarteira).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Integer id) {
        jpaMoedaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Integer id) {
        return jpaMoedaRepository.existsById(id);
    }

    @Override
    public boolean existePorDsMoeda(String dsMoeda) {
        return jpaMoedaRepository.existsByDsMoeda(dsMoeda);
    }

    @Override
    public boolean existePorSiglaMoeda(String siglaMoeda) {
        return jpaMoedaRepository.existsBySiglaMoeda(siglaMoeda);
    }
}
