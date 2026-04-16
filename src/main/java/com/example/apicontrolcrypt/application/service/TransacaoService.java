package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.Transacao;
import com.example.apicontrolcrypt.domain.port.in.TransacaoUseCase;
import com.example.apicontrolcrypt.domain.port.out.CarteiraRepositoryPort;
import com.example.apicontrolcrypt.domain.port.out.MoedaRepositoryPort;
import com.example.apicontrolcrypt.domain.port.out.TransacaoRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoService implements TransacaoUseCase {

    private final TransacaoRepositoryPort transacaoRepositoryPort;
    private final CarteiraRepositoryPort carteiraRepositoryPort;
    private final MoedaRepositoryPort moedaRepositoryPort;

    @Override
    public Transacao criar(Transacao transacao) {
        log.info("Criando transação: codigoTransacao={}, idCarteira={}, idMoeda={}, tpTransacao={}",
                transacao.getCodigoTransacao(), transacao.getIdCarteira(), transacao.getIdMoeda(), transacao.getTpTransacao());
        if (!carteiraRepositoryPort.existePorId(transacao.getIdCarteira())) {
            log.warn("Carteira não encontrada ao criar transação: idCarteira={}", transacao.getIdCarteira());
            throw new ResourceNotFoundException("Carteira", "id", transacao.getIdCarteira());
        }
        if (!moedaRepositoryPort.existePorId(transacao.getIdMoeda())) {
            log.warn("Moeda não encontrada ao criar transação: idMoeda={}", transacao.getIdMoeda());
            throw new ResourceNotFoundException("Moeda", "id", transacao.getIdMoeda());
        }
        Transacao saved = transacaoRepositoryPort.salvar(transacao);
        log.info("Transação criada com sucesso: id={}", saved.getIdTransacao());
        return saved;
    }

    @Override
    public Transacao buscarPorId(Integer id) {
        log.debug("Buscando transação: id={}", id);
        return transacaoRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("Transação não encontrada: id={}", id);
                    return new ResourceNotFoundException("Transação", "id", id);
                });
    }

    @Override
    public List<Transacao> listarTodas() {
        log.debug("Listando todas as transações");
        return transacaoRepositoryPort.buscarTodas();
    }

    @Override
    public List<Transacao> listarPorCarteira(Integer idCarteira) {
        log.debug("Listando transações por carteira: idCarteira={}", idCarteira);
        if (!carteiraRepositoryPort.existePorId(idCarteira)) {
            log.warn("Carteira não encontrada ao listar transações: idCarteira={}", idCarteira);
            throw new ResourceNotFoundException("Carteira", "id", idCarteira);
        }
        return transacaoRepositoryPort.buscarPorIdCarteira(idCarteira);
    }

    @Override
    public List<Transacao> listarPorMoeda(Integer idMoeda) {
        log.debug("Listando transações por moeda: idMoeda={}", idMoeda);
        if (!moedaRepositoryPort.existePorId(idMoeda)) {
            log.warn("Moeda não encontrada ao listar transações: idMoeda={}", idMoeda);
            throw new ResourceNotFoundException("Moeda", "id", idMoeda);
        }
        return transacaoRepositoryPort.buscarPorIdMoeda(idMoeda);
    }

    @Override
    public Transacao atualizar(Integer id, Transacao transacaoUpdate) {
        log.info("Atualizando transação: id={}", id);
        Transacao existing = buscarPorId(id);

        if (transacaoUpdate.getIdCarteira() != null
                && !carteiraRepositoryPort.existePorId(transacaoUpdate.getIdCarteira())) {
            log.warn("Carteira não encontrada ao atualizar transação: idCarteira={}", transacaoUpdate.getIdCarteira());
            throw new ResourceNotFoundException("Carteira", "id", transacaoUpdate.getIdCarteira());
        }
        if (transacaoUpdate.getIdMoeda() != null
                && !moedaRepositoryPort.existePorId(transacaoUpdate.getIdMoeda())) {
            log.warn("Moeda não encontrada ao atualizar transação: idMoeda={}", transacaoUpdate.getIdMoeda());
            throw new ResourceNotFoundException("Moeda", "id", transacaoUpdate.getIdMoeda());
        }

        if (transacaoUpdate.getCodigoTransacao() != null) existing.setCodigoTransacao(transacaoUpdate.getCodigoTransacao());
        if (transacaoUpdate.getIdCarteira() != null) existing.setIdCarteira(transacaoUpdate.getIdCarteira());
        if (transacaoUpdate.getIdMoeda() != null) existing.setIdMoeda(transacaoUpdate.getIdMoeda());
        if (transacaoUpdate.getDtTransacao() != null) existing.setDtTransacao(transacaoUpdate.getDtTransacao());
        if (transacaoUpdate.getTpTransacao() != null) existing.setTpTransacao(transacaoUpdate.getTpTransacao());
        if (transacaoUpdate.getCotacao() != null) existing.setCotacao(transacaoUpdate.getCotacao());
        if (transacaoUpdate.getQuantidade() != null) existing.setQuantidade(transacaoUpdate.getQuantidade());
        if (transacaoUpdate.getValor() != null) existing.setValor(transacaoUpdate.getValor());

        Transacao updated = transacaoRepositoryPort.salvar(existing);
        log.info("Transação atualizada com sucesso: id={}", updated.getIdTransacao());
        return updated;
    }

    @Override
    public void deletar(Integer id) {
        log.info("Deletando transação: id={}", id);
        if (!transacaoRepositoryPort.existePorId(id)) {
            log.warn("Transação não encontrada para deleção: id={}", id);
            throw new ResourceNotFoundException("Transação", "id", id);
        }
        transacaoRepositoryPort.deletarPorId(id);
        log.info("Transação deletada com sucesso: id={}", id);
    }
}
