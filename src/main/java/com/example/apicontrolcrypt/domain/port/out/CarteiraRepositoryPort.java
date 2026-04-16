package com.example.apicontrolcrypt.domain.port.out;

import com.example.apicontrolcrypt.domain.model.Carteira;

import java.util.List;
import java.util.Optional;

public interface CarteiraRepositoryPort {

    Carteira salvar(Carteira carteira);

    Optional<Carteira> buscarPorId(Integer id);

    List<Carteira> buscarTodas();

    void deletarPorId(Integer id);

    boolean existePorId(Integer id);

    boolean existePorDsCarteira(String dsCarteira);
}
