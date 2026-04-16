package com.example.apicontrolcrypt.domain.port.in;

import com.example.apicontrolcrypt.domain.model.Carteira;

import java.util.List;

public interface CarteiraUseCase {

    Carteira criar(Carteira carteira);

    Carteira buscarPorId(Integer id);

    List<Carteira> listarTodas();

    Carteira atualizar(Integer id, Carteira carteira);

    void deletar(Integer id);
}
