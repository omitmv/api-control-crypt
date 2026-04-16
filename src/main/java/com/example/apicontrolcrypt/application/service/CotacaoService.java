package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.Cotacao;
import com.example.apicontrolcrypt.domain.port.in.CotacaoUseCase;
import com.example.apicontrolcrypt.domain.port.out.CarteiraRepositoryPort;
import com.example.apicontrolcrypt.domain.port.out.CotacaoRepositoryPort;
import com.example.apicontrolcrypt.domain.port.out.MoedaRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CotacaoService implements CotacaoUseCase {

    private final CotacaoRepositoryPort cotacaoRepositoryPort;
    private final CarteiraRepositoryPort carteiraRepositoryPort;
    private final MoedaRepositoryPort moedaRepositoryPort;

    @Override
    public Cotacao criar(Cotacao cotacao) {
        log.info("Criando cotação: idCarteira={}, idMoeda={}", cotacao.getIdCarteira(), cotacao.getIdMoeda());
        if (!carteiraRepositoryPort.existePorId(cotacao.getIdCarteira())) {
            log.warn("Carteira não encontrada ao criar cotação: idCarteira={}", cotacao.getIdCarteira());
            throw new ResourceNotFoundException("Carteira", "id", cotacao.getIdCarteira());
        }
        if (!moedaRepositoryPort.existePorId(cotacao.getIdMoeda())) {
            log.warn("Moeda não encontrada ao criar cotação: idMoeda={}", cotacao.getIdMoeda());
            throw new ResourceNotFoundException("Moeda", "id", cotacao.getIdMoeda());
        }
        cotacao.setDtUltAtualizacao(LocalDateTime.now());
        Cotacao saved = cotacaoRepositoryPort.salvar(cotacao);
        log.info("Cotação criada com sucesso: id={}", saved.getIdCotacao());
        return saved;
    }

    @Override
    public Cotacao buscarPorId(Integer id) {
        log.debug("Buscando cotação: id={}", id);
        return cotacaoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("Cotação não encontrada: id={}", id);
                    return new ResourceNotFoundException("Cotação", "id", id);
                });
    }

    @Override
    public List<Cotacao> listarTodas() {
        log.debug("Listando todas as cotações");
        return cotacaoRepositoryPort.buscarTodas();
    }

    @Override
    public List<Cotacao> listarPorCarteira(Integer idCarteira) {
        log.debug("Listando cotações por carteira: idCarteira={}", idCarteira);
        if (!carteiraRepositoryPort.existePorId(idCarteira)) {
            log.warn("Carteira não encontrada ao listar cotações: idCarteira={}", idCarteira);
            throw new ResourceNotFoundException("Carteira", "id", idCarteira);
        }
        return cotacaoRepositoryPort.buscarPorIdCarteira(idCarteira);
    }

    @Override
    public List<Cotacao> listarPorMoeda(Integer idMoeda) {
        log.debug("Listando cotações por moeda: idMoeda={}", idMoeda);
        if (!moedaRepositoryPort.existePorId(idMoeda)) {
            log.warn("Moeda não encontrada ao listar cotações: idMoeda={}", idMoeda);
            throw new ResourceNotFoundException("Moeda", "id", idMoeda);
        }
        return cotacaoRepositoryPort.buscarPorIdMoeda(idMoeda);
    }

    @Override
    public Cotacao atualizar(Integer id, Cotacao cotacaoUpdate) {
        log.info("Atualizando cotação: id={}", id);
        Cotacao existing = buscarPorId(id);

        if (cotacaoUpdate.getIdCarteira() != null
                && !carteiraRepositoryPort.existePorId(cotacaoUpdate.getIdCarteira())) {
            log.warn("Carteira não encontrada ao atualizar cotação: idCarteira={}", cotacaoUpdate.getIdCarteira());
            throw new ResourceNotFoundException("Carteira", "id", cotacaoUpdate.getIdCarteira());
        }
        if (cotacaoUpdate.getIdMoeda() != null
                && !moedaRepositoryPort.existePorId(cotacaoUpdate.getIdMoeda())) {
            log.warn("Moeda não encontrada ao atualizar cotação: idMoeda={}", cotacaoUpdate.getIdMoeda());
            throw new ResourceNotFoundException("Moeda", "id", cotacaoUpdate.getIdMoeda());
        }

        if (cotacaoUpdate.getIdCarteira() != null) existing.setIdCarteira(cotacaoUpdate.getIdCarteira());
        if (cotacaoUpdate.getIdMoeda() != null) existing.setIdMoeda(cotacaoUpdate.getIdMoeda());
        if (cotacaoUpdate.getCotacao() != null) existing.setCotacao(cotacaoUpdate.getCotacao());
        if (cotacaoUpdate.getPerc24h() != null) existing.setPerc24h(cotacaoUpdate.getPerc24h());
        existing.setDtUltAtualizacao(LocalDateTime.now());

        Cotacao updated = cotacaoRepositoryPort.salvar(existing);
        log.info("Cotação atualizada com sucesso: id={}", updated.getIdCotacao());
        return updated;
    }

    @Override
    public void deletar(Integer id) {
        log.info("Deletando cotação: id={}", id);
        if (!cotacaoRepositoryPort.existePorId(id)) {
            log.warn("Cotação não encontrada para deleção: id={}", id);
            throw new ResourceNotFoundException("Cotação", "id", id);
        }
        cotacaoRepositoryPort.deletarPorId(id);
        log.info("Cotação deletada com sucesso: id={}", id);
    }
}
