package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.Moeda;
import com.example.apicontrolcrypt.domain.port.in.MoedaUseCase;
import com.example.apicontrolcrypt.domain.port.out.CarteiraRepositoryPort;
import com.example.apicontrolcrypt.domain.port.out.MoedaRepositoryPort;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.exception.DuplicateResourceException;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoedaService implements MoedaUseCase {

    private final MoedaRepositoryPort moedaRepositoryPort;
    private final CarteiraRepositoryPort carteiraRepositoryPort;

    @Override
    public Moeda criar(Moeda moeda) {
        log.info("Criando moeda: dsMoeda={}, siglaMoeda={}, idCarteira={}", moeda.getDsMoeda(), moeda.getSiglaMoeda(), moeda.getIdCarteira());
        if (!carteiraRepositoryPort.existePorId(moeda.getIdCarteira())) {
            log.warn("Carteira não encontrada ao criar moeda: idCarteira={}", moeda.getIdCarteira());
            throw new ResourceNotFoundException("Carteira", "id", moeda.getIdCarteira());
        }
        if (moedaRepositoryPort.existePorDsMoeda(moeda.getDsMoeda())) {
            log.warn("Moeda duplicada: dsMoeda={}", moeda.getDsMoeda());
            throw new DuplicateResourceException("Moeda", "dsMoeda", moeda.getDsMoeda());
        }
        if (moedaRepositoryPort.existePorSiglaMoeda(moeda.getSiglaMoeda())) {
            log.warn("Moeda duplicada: siglaMoeda={}", moeda.getSiglaMoeda());
            throw new DuplicateResourceException("Moeda", "siglaMoeda", moeda.getSiglaMoeda());
        }
        moeda.setDtCadastro(LocalDateTime.now());
        Moeda saved = moedaRepositoryPort.salvar(moeda);
        log.info("Moeda criada com sucesso: id={}", saved.getIdMoeda());
        return saved;
    }

    @Override
    public Moeda buscarPorId(Integer id) {
        log.debug("Buscando moeda: id={}", id);
        return moedaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("Moeda não encontrada: id={}", id);
                    return new ResourceNotFoundException("Moeda", "id", id);
                });
    }

    @Override
    public List<Moeda> listarTodas() {
        log.debug("Listando todas as moedas");
        return moedaRepositoryPort.buscarTodas();
    }

    @Override
    public List<Moeda> listarPorCarteira(Integer idCarteira) {
        log.debug("Listando moedas por carteira: idCarteira={}", idCarteira);
        if (!carteiraRepositoryPort.existePorId(idCarteira)) {
            log.warn("Carteira não encontrada ao listar moedas: idCarteira={}", idCarteira);
            throw new ResourceNotFoundException("Carteira", "id", idCarteira);
        }
        return moedaRepositoryPort.buscarPorIdCarteira(idCarteira);
    }

    @Override
    public Moeda atualizar(Integer id, Moeda moedaUpdate) {
        log.info("Atualizando moeda: id={}", id);
        Moeda existing = buscarPorId(id);

        if (moedaUpdate.getIdCarteira() != null
                && !carteiraRepositoryPort.existePorId(moedaUpdate.getIdCarteira())) {
            log.warn("Carteira não encontrada ao atualizar moeda: idCarteira={}", moedaUpdate.getIdCarteira());
            throw new ResourceNotFoundException("Carteira", "id", moedaUpdate.getIdCarteira());
        }
        if (moedaUpdate.getDsMoeda() != null
                && !moedaUpdate.getDsMoeda().equals(existing.getDsMoeda())
                && moedaRepositoryPort.existePorDsMoeda(moedaUpdate.getDsMoeda())) {
            log.warn("Moeda duplicada ao atualizar: dsMoeda={}", moedaUpdate.getDsMoeda());
            throw new DuplicateResourceException("Moeda", "dsMoeda", moedaUpdate.getDsMoeda());
        }
        if (moedaUpdate.getSiglaMoeda() != null
                && !moedaUpdate.getSiglaMoeda().equals(existing.getSiglaMoeda())
                && moedaRepositoryPort.existePorSiglaMoeda(moedaUpdate.getSiglaMoeda())) {
            log.warn("Moeda duplicada ao atualizar: siglaMoeda={}", moedaUpdate.getSiglaMoeda());
            throw new DuplicateResourceException("Moeda", "siglaMoeda", moedaUpdate.getSiglaMoeda());
        }

        if (moedaUpdate.getDsMoeda() != null) existing.setDsMoeda(moedaUpdate.getDsMoeda());
        if (moedaUpdate.getSiglaMoeda() != null) existing.setSiglaMoeda(moedaUpdate.getSiglaMoeda());
        if (moedaUpdate.getCodigoCarteira() != null) existing.setCodigoCarteira(moedaUpdate.getCodigoCarteira());
        if (moedaUpdate.getIdCarteira() != null) existing.setIdCarteira(moedaUpdate.getIdCarteira());

        Moeda updated = moedaRepositoryPort.salvar(existing);
        log.info("Moeda atualizada com sucesso: id={}", updated.getIdMoeda());
        return updated;
    }

    @Override
    public void deletar(Integer id) {
        log.info("Deletando moeda: id={}", id);
        if (!moedaRepositoryPort.existePorId(id)) {
            log.warn("Moeda não encontrada para deleção: id={}", id);
            throw new ResourceNotFoundException("Moeda", "id", id);
        }
        moedaRepositoryPort.deletarPorId(id);
        log.info("Moeda deletada com sucesso: id={}", id);
    }
}
