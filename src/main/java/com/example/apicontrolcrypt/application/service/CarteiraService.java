package com.example.apicontrolcrypt.application.service;

import com.example.apicontrolcrypt.domain.model.Carteira;
import com.example.apicontrolcrypt.domain.port.in.CarteiraUseCase;
import com.example.apicontrolcrypt.domain.port.out.CarteiraRepositoryPort;
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
public class CarteiraService implements CarteiraUseCase {

    private final CarteiraRepositoryPort carteiraRepositoryPort;

    @Override
    public Carteira criar(Carteira carteira) {
        log.info("Criando carteira: dsCarteira={}", carteira.getDsCarteira());
        if (carteiraRepositoryPort.existePorDsCarteira(carteira.getDsCarteira())) {
            log.warn("Carteira duplicada: dsCarteira={}", carteira.getDsCarteira());
            throw new DuplicateResourceException("Carteira", "dsCarteira", carteira.getDsCarteira());
        }
        carteira.setDtCadastro(LocalDateTime.now());
        Carteira saved = carteiraRepositoryPort.salvar(carteira);
        log.info("Carteira criada com sucesso: id={}", saved.getIdCarteira());
        return saved;
    }

    @Override
    public Carteira buscarPorId(Integer id) {
        log.debug("Buscando carteira: id={}", id);
        return carteiraRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> {
                    log.warn("Carteira não encontrada: id={}", id);
                    return new ResourceNotFoundException("Carteira", "id", id);
                });
    }

    @Override
    public List<Carteira> listarTodas() {
        log.debug("Listando todas as carteiras");
        return carteiraRepositoryPort.buscarTodas();
    }

    @Override
    public Carteira atualizar(Integer id, Carteira carteiraUpdate) {
        log.info("Atualizando carteira: id={}", id);
        Carteira existing = buscarPorId(id);

        if (carteiraUpdate.getDsCarteira() != null
                && !carteiraUpdate.getDsCarteira().equals(existing.getDsCarteira())
                && carteiraRepositoryPort.existePorDsCarteira(carteiraUpdate.getDsCarteira())) {
            log.warn("Carteira duplicada ao atualizar: dsCarteira={}", carteiraUpdate.getDsCarteira());
            throw new DuplicateResourceException("Carteira", "dsCarteira", carteiraUpdate.getDsCarteira());
        }

        if (carteiraUpdate.getDsCarteira() != null) {
            existing.setDsCarteira(carteiraUpdate.getDsCarteira());
        }
        existing.setDtUltAtualizacao(LocalDateTime.now());
        Carteira updated = carteiraRepositoryPort.salvar(existing);
        log.info("Carteira atualizada com sucesso: id={}", updated.getIdCarteira());
        return updated;
    }

    @Override
    public void deletar(Integer id) {
        log.info("Deletando carteira: id={}", id);
        if (!carteiraRepositoryPort.existePorId(id)) {
            log.warn("Carteira não encontrada para deleção: id={}", id);
            throw new ResourceNotFoundException("Carteira", "id", id);
        }
        carteiraRepositoryPort.deletarPorId(id);
        log.info("Carteira deletada com sucesso: id={}", id);
    }
}
