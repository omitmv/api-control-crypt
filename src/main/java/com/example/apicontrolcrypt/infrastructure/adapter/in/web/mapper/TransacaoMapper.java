package com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper;

import com.example.apicontrolcrypt.domain.model.Transacao;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao.CreateTransacaoRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao.TransacaoResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao.UpdateTransacaoRequest;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    public Transacao toDomain(CreateTransacaoRequest request) {
        return Transacao.builder()
                .codigoTransacao(request.getCodigoTransacao())
                .idCarteira(request.getIdCarteira())
                .idMoeda(request.getIdMoeda())
                .dtTransacao(request.getDtTransacao())
                .tpTransacao(request.getTpTransacao())
                .cotacao(request.getCotacao())
                .quantidade(request.getQuantidade())
                .valor(request.getValor())
                .build();
    }

    public Transacao toDomain(UpdateTransacaoRequest request, Transacao existing) {
        return Transacao.builder()
                .idTransacao(existing.getIdTransacao())
                .codigoTransacao(request.getCodigoTransacao() != null ? request.getCodigoTransacao() : existing.getCodigoTransacao())
                .idCarteira(request.getIdCarteira() != null ? request.getIdCarteira() : existing.getIdCarteira())
                .idMoeda(request.getIdMoeda() != null ? request.getIdMoeda() : existing.getIdMoeda())
                .dtTransacao(request.getDtTransacao() != null ? request.getDtTransacao() : existing.getDtTransacao())
                .tpTransacao(request.getTpTransacao() != null ? request.getTpTransacao() : existing.getTpTransacao())
                .cotacao(request.getCotacao() != null ? request.getCotacao() : existing.getCotacao())
                .quantidade(request.getQuantidade() != null ? request.getQuantidade() : existing.getQuantidade())
                .valor(request.getValor() != null ? request.getValor() : existing.getValor())
                .build();
    }

    public TransacaoResponse toResponse(Transacao domain) {
        return TransacaoResponse.builder()
                .idTransacao(domain.getIdTransacao())
                .codigoTransacao(domain.getCodigoTransacao())
                .idCarteira(domain.getIdCarteira())
                .idMoeda(domain.getIdMoeda())
                .dtTransacao(domain.getDtTransacao())
                .tpTransacao(domain.getTpTransacao())
                .cotacao(domain.getCotacao())
                .quantidade(domain.getQuantidade())
                .valor(domain.getValor())
                .build();
    }
}
