package com.example.apicontrolcrypt.domain.port.out;

import com.example.apicontrolcrypt.domain.model.Transacao;

import java.util.List;
import java.util.Optional;

public interface TransacaoRepositoryPort {

    Transacao salvar(Transacao transacao);

    Optional<Transacao> buscarPorId(Integer id);

    List<Transacao> buscarTodas();

    List<Transacao> buscarPorIdCarteira(Integer idCarteira);

    List<Transacao> buscarPorIdMoeda(Integer idMoeda);

    void deletarPorId(Integer id);

    boolean existePorId(Integer id);
}
