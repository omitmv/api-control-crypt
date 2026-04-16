package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para atualização de uma moeda")
public class UpdateMoedaRequest {

    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
    @Schema(description = "Nova descrição/nome da moeda", example = "Bitcoin")
    private String dsMoeda;

    @Size(max = 10, message = "A sigla deve ter no máximo 10 caracteres")
    @Schema(description = "Nova sigla/ticker da moeda", example = "BTC")
    private String siglaMoeda;

    @Size(max = 100, message = "O código da carteira deve ter no máximo 100 caracteres")
    @Schema(description = "Novo código da moeda na carteira", example = "bitcoin")
    private String codigoCarteira;

    @Schema(description = "Novo identificador da carteira associada", example = "2")
    private Integer idCarteira;
}
