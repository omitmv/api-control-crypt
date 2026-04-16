package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence;

import com.example.apicontrolcrypt.domain.model.Transacao;
import com.example.apicontrolcrypt.domain.port.out.TransacaoRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper.TransacaoEntityMapper;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository.JpaTransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransacaoRepositoryAdapter implements TransacaoRepositoryPort {

    private final JpaTransacaoRepository jpaTransacaoRepository;
    private final TransacaoEntityMapper mapper;

    @Override
    public Transacao salvar(Transacao transacao) {
        return mapper.toDomain(jpaTransacaoRepository.save(mapper.toEntity(transacao)));
    }

    @Override
    public Optional<Transacao> buscarPorId(Integer id) {
        return jpaTransacaoRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Transacao> buscarTodas() {
        return jpaTransacaoRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transacao> buscarPorIdCarteira(Integer idCarteira) {
        return jpaTransacaoRepository.findByCarteira_IdCarteira(idCarteira).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transacao> buscarPorIdMoeda(Integer idMoeda) {
        return jpaTransacaoRepository.findByMoeda_IdMoeda(idMoeda).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Integer id) {
        jpaTransacaoRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Integer id) {
        return jpaTransacaoRepository.existsById(id);
    }
}
