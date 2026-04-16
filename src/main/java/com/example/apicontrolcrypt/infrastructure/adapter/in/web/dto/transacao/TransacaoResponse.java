package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno de uma transação")
public class TransacaoResponse {

    @Schema(description = "Identificador único da transação", example = "1")
    private Integer idTransacao;

    @Schema(description = "Código da transação", example = "TXN-20260415-001")
    private String codigoTransacao;

    @Schema(description = "Identificador da carteira associada", example = "1")
    private Integer idCarteira;

    @Schema(description = "Identificador da moeda associada", example = "1")
    private Integer idMoeda;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data e hora da transação", example = "2026-04-15T10:30:00")
    private LocalDateTime dtTransacao;

    @Schema(description = "Tipo da transação: C=Compra, V=Venda", example = "C")
    private String tpTransacao;

    @Schema(description = "Cotação no momento da transação em centavos", example = "30000000")
    private Long cotacao;

    @Schema(description = "Quantidade da moeda transacionada", example = "100000000")
    private Long quantidade;

    @Schema(description = "Valor total da transação em centavos", example = "3000000000")
    private Long valor;
}
