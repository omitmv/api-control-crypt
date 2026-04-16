package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para criação de uma cotação")
public class CreateCotacaoRequest {

    @NotNull(message = "O id da carteira é obrigatório")
    @Schema(description = "Identificador da carteira associada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idCarteira;

    @NotNull(message = "O id da moeda é obrigatório")
    @Schema(description = "Identificador da moeda associada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idMoeda;

    @NotNull(message = "O valor da cotação é obrigatório")
    @Schema(description = "Valor da cotação em centavos (bigint)", example = "30000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long cotacao;

    @DecimalMin(value = "-999999999.99", message = "Percentual 24h inválido")
    @Schema(description = "Variação percentual nas últimas 24h", example = "2.54")
    private BigDecimal perc24h;
}
