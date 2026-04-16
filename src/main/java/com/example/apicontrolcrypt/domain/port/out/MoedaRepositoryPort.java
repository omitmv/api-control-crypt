package com.example.apicontrolcrypt.domain.port.out;

import com.example.apicontrolcrypt.domain.model.Moeda;

import java.util.List;
import java.util.Optional;

public interface MoedaRepositoryPort {

    Moeda salvar(Moeda moeda);

    Optional<Moeda> buscarPorId(Integer id);

    List<Moeda> buscarTodas();

    List<Moeda> buscarPorIdCarteira(Integer idCarteira);

    void deletarPorId(Integer id);

    boolean existePorId(Integer id);

    boolean existePorDsMoeda(String dsMoeda);

    boolean existePorSiglaMoeda(String siglaMoeda);
}
