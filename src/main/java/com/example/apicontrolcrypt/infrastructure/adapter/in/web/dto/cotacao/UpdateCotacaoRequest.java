package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para atualização de uma cotação")
public class UpdateCotacaoRequest {

    @Schema(description = "Novo identificador da carteira associada", example = "1")
    private Integer idCarteira;

    @Schema(description = "Novo identificador da moeda associada", example = "1")
    private Integer idMoeda;

    @Schema(description = "Novo valor da cotação em centavos (bigint)", example = "31000000")
    private Long cotacao;

    @Schema(description = "Nova variação percentual nas últimas 24h", example = "-1.23")
    private BigDecimal perc24h;
}
