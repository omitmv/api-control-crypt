package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository;

import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.MoedaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMoedaRepository extends JpaRepository<MoedaEntity, Integer> {

    List<MoedaEntity> findByCarteira_IdCarteira(Integer idCarteira);

    boolean existsByDsMoeda(String dsMoeda);

    boolean existsBySiglaMoeda(String siglaMoeda);
}
