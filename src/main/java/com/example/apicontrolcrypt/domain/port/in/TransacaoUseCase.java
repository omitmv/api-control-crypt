package com.example.apicontrolcrypt.domain.port.in;

import com.example.apicontrolcrypt.domain.model.Transacao;

import java.util.List;

public interface TransacaoUseCase {

    Transacao criar(Transacao transacao);

    Transacao buscarPorId(Integer id);

    List<Transacao> listarTodas();

    List<Transacao> listarPorCarteira(Integer idCarteira);

    List<Transacao> listarPorMoeda(Integer idMoeda);

    Transacao atualizar(Integer id, Transacao transacao);

    void deletar(Integer id);
}
