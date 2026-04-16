package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda;

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
@Schema(description = "Dados de retorno de uma moeda")
public class MoedaResponse {

    @Schema(description = "Identificador único da moeda", example = "1")
    private Integer idMoeda;

    @Schema(description = "Descrição/nome da moeda", example = "Bitcoin")
    private String dsMoeda;

    @Schema(description = "Sigla/ticker da moeda", example = "BTC")
    private String siglaMoeda;

    @Schema(description = "Código da moeda na carteira", example = "bitcoin")
    private String codigoCarteira;

    @Schema(description = "Identificador da carteira associada", example = "1")
    private Integer idCarteira;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data de cadastro", example = "2026-04-15T10:00:00")
    private LocalDateTime dtCadastro;
}
