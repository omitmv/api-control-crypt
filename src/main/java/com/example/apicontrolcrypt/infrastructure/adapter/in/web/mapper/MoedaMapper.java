package com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper;

import com.example.apicontrolcrypt.domain.model.Moeda;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda.CreateMoedaRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda.MoedaResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda.UpdateMoedaRequest;
import org.springframework.stereotype.Component;

@Component
public class MoedaMapper {

    public Moeda toDomain(CreateMoedaRequest request) {
        return Moeda.builder()
                .dsMoeda(request.getDsMoeda())
                .siglaMoeda(request.getSiglaMoeda())
                .codigoCarteira(request.getCodigoCarteira())
                .idCarteira(request.getIdCarteira())
                .build();
    }

    public Moeda toDomain(UpdateMoedaRequest request, Moeda existing) {
        return Moeda.builder()
                .idMoeda(existing.getIdMoeda())
                .dsMoeda(request.getDsMoeda() != null ? request.getDsMoeda() : existing.getDsMoeda())
                .siglaMoeda(request.getSiglaMoeda() != null ? request.getSiglaMoeda() : existing.getSiglaMoeda())
                .codigoCarteira(request.getCodigoCarteira() != null ? request.getCodigoCarteira() : existing.getCodigoCarteira())
                .idCarteira(request.getIdCarteira() != null ? request.getIdCarteira() : existing.getIdCarteira())
                .dtCadastro(existing.getDtCadastro())
                .build();
    }

    public MoedaResponse toResponse(Moeda domain) {
        return MoedaResponse.builder()
                .idMoeda(domain.getIdMoeda())
                .dsMoeda(domain.getDsMoeda())
                .siglaMoeda(domain.getSiglaMoeda())
                .codigoCarteira(domain.getCodigoCarteira())
                .idCarteira(domain.getIdCarteira())
                .dtCadastro(domain.getDtCadastro())
                .build();
    }
}
