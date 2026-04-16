package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira;

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
@Schema(description = "Dados de retorno de uma carteira")
public class CarteiraResponse {

    @Schema(description = "Identificador único da carteira", example = "1")
    private Integer idCarteira;

    @Schema(description = "Descrição/nome da carteira", example = "Carteira Binance")
    private String dsCarteira;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data de cadastro", example = "2026-04-15T10:00:00")
    private LocalDateTime dtCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data da última atualização", example = "2026-04-15T12:00:00")
    private LocalDateTime dtUltAtualizacao;
}
