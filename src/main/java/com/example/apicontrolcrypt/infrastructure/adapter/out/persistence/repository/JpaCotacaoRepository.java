package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository;

import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.CotacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCotacaoRepository extends JpaRepository<CotacaoEntity, Integer> {

    List<CotacaoEntity> findByCarteira_IdCarteira(Integer idCarteira);

    List<CotacaoEntity> findByMoeda_IdMoeda(Integer idMoeda);
}
