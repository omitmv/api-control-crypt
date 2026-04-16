package com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper;

import com.example.apicontrolcrypt.domain.model.Cotacao;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao.CotacaoResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao.CreateCotacaoRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao.UpdateCotacaoRequest;
import org.springframework.stereotype.Component;

@Component
public class CotacaoMapper {

    public Cotacao toDomain(CreateCotacaoRequest request) {
        return Cotacao.builder()
                .idCarteira(request.getIdCarteira())
                .idMoeda(request.getIdMoeda())
                .cotacao(request.getCotacao())
                .perc24h(request.getPerc24h())
                .build();
    }

    public Cotacao toDomain(UpdateCotacaoRequest request, Cotacao existing) {
        return Cotacao.builder()
                .idCotacao(existing.getIdCotacao())
                .idCarteira(request.getIdCarteira() != null ? request.getIdCarteira() : existing.getIdCarteira())
                .idMoeda(request.getIdMoeda() != null ? request.getIdMoeda() : existing.getIdMoeda())
                .cotacao(request.getCotacao() != null ? request.getCotacao() : existing.getCotacao())
                .perc24h(request.getPerc24h() != null ? request.getPerc24h() : existing.getPerc24h())
                .dtUltAtualizacao(existing.getDtUltAtualizacao())
                .build();
    }

    public CotacaoResponse toResponse(Cotacao domain) {
        return CotacaoResponse.builder()
                .idCotacao(domain.getIdCotacao())
                .idCarteira(domain.getIdCarteira())
                .idMoeda(domain.getIdMoeda())
                .dtUltAtualizacao(domain.getDtUltAtualizacao())
                .cotacao(domain.getCotacao())
                .perc24h(domain.getPerc24h())
                .build();
    }
}
