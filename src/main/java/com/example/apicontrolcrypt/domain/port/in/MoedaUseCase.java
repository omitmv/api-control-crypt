package com.example.apicontrolcrypt.domain.port.in;

import com.example.apicontrolcrypt.domain.model.Moeda;

import java.util.List;

public interface MoedaUseCase {

    Moeda criar(Moeda moeda);

    Moeda buscarPorId(Integer id);

    List<Moeda> listarTodas();

    List<Moeda> listarPorCarteira(Integer idCarteira);

    Moeda atualizar(Integer id, Moeda moeda);

    void deletar(Integer id);
}
