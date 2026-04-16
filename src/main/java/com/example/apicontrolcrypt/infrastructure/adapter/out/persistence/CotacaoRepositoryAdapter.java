package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence;

import com.example.apicontrolcrypt.domain.model.Cotacao;
import com.example.apicontrolcrypt.domain.port.out.CotacaoRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper.CotacaoEntityMapper;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository.JpaCotacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CotacaoRepositoryAdapter implements CotacaoRepositoryPort {

    private final JpaCotacaoRepository jpaCotacaoRepository;
    private final CotacaoEntityMapper mapper;

    @Override
    public Cotacao salvar(Cotacao cotacao) {
        return mapper.toDomain(jpaCotacaoRepository.save(mapper.toEntity(cotacao)));
    }

    @Override
    public Optional<Cotacao> buscarPorId(Integer id) {
        return jpaCotacaoRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Cotacao> buscarTodas() {
        return jpaCotacaoRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cotacao> buscarPorIdCarteira(Integer idCarteira) {
        return jpaCotacaoRepository.findByCarteira_IdCarteira(idCarteira).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cotacao> buscarPorIdMoeda(Integer idMoeda) {
        return jpaCotacaoRepository.findByMoeda_IdMoeda(idMoeda).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Integer id) {
        jpaCotacaoRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Integer id) {
        return jpaCotacaoRepository.existsById(id);
    }
}
