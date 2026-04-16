package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper;

import com.example.apicontrolcrypt.domain.model.Carteira;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.CarteiraEntity;
import org.springframework.stereotype.Component;

@Component
public class CarteiraEntityMapper {

    public CarteiraEntity toEntity(Carteira domain) {
        return CarteiraEntity.builder()
                .idCarteira(domain.getIdCarteira())
                .dsCarteira(domain.getDsCarteira())
                .dtCadastro(domain.getDtCadastro())
                .dtUltAtualizacao(domain.getDtUltAtualizacao())
                .build();
    }

    public Carteira toDomain(CarteiraEntity entity) {
        return Carteira.builder()
                .idCarteira(entity.getIdCarteira())
                .dsCarteira(entity.getDsCarteira())
                .dtCadastro(entity.getDtCadastro())
                .dtUltAtualizacao(entity.getDtUltAtualizacao())
                .build();
    }
}
