package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper;

import com.example.apicontrolcrypt.domain.model.Cotacao;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.CarteiraEntity;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.CotacaoEntity;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.MoedaEntity;
import org.springframework.stereotype.Component;

@Component
public class CotacaoEntityMapper {

    public CotacaoEntity toEntity(Cotacao domain) {
        CarteiraEntity carteira = CarteiraEntity.builder()
                .idCarteira(domain.getIdCarteira())
                .build();
        MoedaEntity moeda = MoedaEntity.builder()
                .idMoeda(domain.getIdMoeda())
                .build();
        return CotacaoEntity.builder()
                .idCotacao(domain.getIdCotacao())
                .carteira(carteira)
                .moeda(moeda)
                .dtUltAtualizacao(domain.getDtUltAtualizacao())
                .cotacao(domain.getCotacao())
                .perc24h(domain.getPerc24h())
                .build();
    }

    public Cotacao toDomain(CotacaoEntity entity) {
        return Cotacao.builder()
                .idCotacao(entity.getIdCotacao())
                .idCarteira(entity.getCarteira() != null ? entity.getCarteira().getIdCarteira() : null)
                .idMoeda(entity.getMoeda() != null ? entity.getMoeda().getIdMoeda() : null)
                .dtUltAtualizacao(entity.getDtUltAtualizacao())
                .cotacao(entity.getCotacao())
                .perc24h(entity.getPerc24h())
                .build();
    }
}
