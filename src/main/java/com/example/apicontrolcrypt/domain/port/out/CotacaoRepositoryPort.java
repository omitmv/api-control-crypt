package com.example.apicontrolcrypt.domain.port.out;

import com.example.apicontrolcrypt.domain.model.Cotacao;

import java.util.List;
import java.util.Optional;

public interface CotacaoRepositoryPort {

    Cotacao salvar(Cotacao cotacao);

    Optional<Cotacao> buscarPorId(Integer id);

    List<Cotacao> buscarTodas();

    List<Cotacao> buscarPorIdCarteira(Integer idCarteira);

    List<Cotacao> buscarPorIdMoeda(Integer idMoeda);

    void deletarPorId(Integer id);

    boolean existePorId(Integer id);
}
