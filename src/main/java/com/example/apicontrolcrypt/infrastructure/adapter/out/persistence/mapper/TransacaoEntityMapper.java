package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.mapper;

import com.example.apicontrolcrypt.domain.model.Transacao;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.CarteiraEntity;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.MoedaEntity;
import com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity.TransacaoEntity;
import org.springframework.stereotype.Component;

@Component
public class TransacaoEntityMapper {

    public TransacaoEntity toEntity(Transacao domain) {
        CarteiraEntity carteira = CarteiraEntity.builder()
                .idCarteira(domain.getIdCarteira())
                .build();
        MoedaEntity moeda = MoedaEntity.builder()
                .idMoeda(domain.getIdMoeda())
                .build();
        return TransacaoEntity.builder()
                .idTransacao(domain.getIdTransacao())
                .codigoTransacao(domain.getCodigoTransacao())
                .carteira(carteira)
                .moeda(moeda)
                .dtTransacao(domain.getDtTransacao())
                .tpTransacao(domain.getTpTransacao())
                .cotacao(domain.getCotacao())
                .quantidade(domain.getQuantidade())
                .valor(domain.getValor())
                .build();
    }

    public Transacao toDomain(TransacaoEntity entity) {
        return Transacao.builder()
                .idTransacao(entity.getIdTransacao())
                .codigoTransacao(entity.getCodigoTransacao())
                .idCarteira(entity.getCarteira() != null ? entity.getCarteira().getIdCarteira() : null)
                .idMoeda(entity.getMoeda() != null ? entity.getMoeda().getIdMoeda() : null)
                .dtTransacao(entity.getDtTransacao())
                .tpTransacao(entity.getTpTransacao())
                .cotacao(entity.getCotacao())
                .quantidade(entity.getQuantidade())
                .valor(entity.getValor())
                .build();
    }
}
