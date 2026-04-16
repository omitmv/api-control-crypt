package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno de uma cotação")
public class CotacaoResponse {

    @Schema(description = "Identificador único da cotação", example = "1")
    private Integer idCotacao;

    @Schema(description = "Identificador da carteira associada", example = "1")
    private Integer idCarteira;

    @Schema(description = "Identificador da moeda associada", example = "1")
    private Integer idMoeda;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data da última atualização da cotação", example = "2026-04-15T12:00:00")
    private LocalDateTime dtUltAtualizacao;

    @Schema(description = "Valor da cotação em centavos", example = "30000000")
    private Long cotacao;

    @Schema(description = "Variação percentual nas últimas 24h", example = "2.54")
    private BigDecimal perc24h;
}
