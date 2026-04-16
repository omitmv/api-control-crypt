package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper;

import com.example.apicontrolcrypt.domain.model.Moeda;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.CarteiraEntity;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.MoedaEntity;
import org.springframework.stereotype.Component;

@Component
public class MoedaEntityMapper {

    public MoedaEntity toEntity(Moeda domain) {
        CarteiraEntity carteira = CarteiraEntity.builder()
                .idCarteira(domain.getIdCarteira())
                .build();
        return MoedaEntity.builder()
                .idMoeda(domain.getIdMoeda())
                .dsMoeda(domain.getDsMoeda())
                .siglaMoeda(domain.getSiglaMoeda())
                .codigoCarteira(domain.getCodigoCarteira())
                .carteira(carteira)
                .dtCadastro(domain.getDtCadastro())
                .build();
    }

    public Moeda toDomain(MoedaEntity entity) {
        return Moeda.builder()
                .idMoeda(entity.getIdMoeda())
                .dsMoeda(entity.getDsMoeda())
                .siglaMoeda(entity.getSiglaMoeda())
                .codigoCarteira(entity.getCodigoCarteira())
                .idCarteira(entity.getCarteira() != null ? entity.getCarteira().getIdCarteira() : null)
                .dtCadastro(entity.getDtCadastro())
                .build();
    }
}
