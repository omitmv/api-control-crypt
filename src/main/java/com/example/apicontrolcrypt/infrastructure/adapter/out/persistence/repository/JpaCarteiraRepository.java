package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.repository;

import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.CarteiraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCarteiraRepository extends JpaRepository<CarteiraEntity, Integer> {

    boolean existsByDsCarteira(String dsCarteira);
}
