package com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper;

import com.example.apicontrolcrypt.domain.model.Carteira;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira.CarteiraResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira.CreateCarteiraRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira.UpdateCarteiraRequest;
import org.springframework.stereotype.Component;

@Component
public class CarteiraMapper {

    public Carteira toDomain(CreateCarteiraRequest request) {
        return Carteira.builder()
                .dsCarteira(request.getDsCarteira())
                .build();
    }

    public Carteira toDomain(UpdateCarteiraRequest request, Carteira existing) {
        return Carteira.builder()
                .idCarteira(existing.getIdCarteira())
                .dsCarteira(request.getDsCarteira() != null ? request.getDsCarteira() : existing.getDsCarteira())
                .dtCadastro(existing.getDtCadastro())
                .dtUltAtualizacao(existing.getDtUltAtualizacao())
                .build();
    }

    public CarteiraResponse toResponse(Carteira domain) {
        return CarteiraResponse.builder()
                .idCarteira(domain.getIdCarteira())
                .dsCarteira(domain.getDsCarteira())
                .dtCadastro(domain.getDtCadastro())
                .dtUltAtualizacao(domain.getDtUltAtualizacao())
                .build();
    }
}
