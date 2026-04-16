package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository;

import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTransacaoRepository extends JpaRepository<TransacaoEntity, Integer> {

    List<TransacaoEntity> findByCarteira_IdCarteira(Integer idCarteira);

    List<TransacaoEntity> findByMoeda_IdMoeda(Integer idMoeda);
}
