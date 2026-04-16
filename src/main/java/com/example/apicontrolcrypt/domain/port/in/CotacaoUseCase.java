package com.example.apicontrolcrypt.domain.port.in;

import com.example.apicontrolcrypt.domain.model.Cotacao;

import java.util.List;

public interface CotacaoUseCase {

    Cotacao criar(Cotacao cotacao);

    Cotacao buscarPorId(Integer id);

    List<Cotacao> listarTodas();

    List<Cotacao> listarPorCarteira(Integer idCarteira);

    List<Cotacao> listarPorMoeda(Integer idMoeda);

    Cotacao atualizar(Integer id, Cotacao cotacao);

    void deletar(Integer id);
}
