package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para criação de uma moeda")
public class CreateMoedaRequest {

    @NotBlank(message = "A descrição da moeda é obrigatória")
    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
    @Schema(description = "Descrição/nome da moeda", example = "Bitcoin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dsMoeda;

    @NotBlank(message = "A sigla da moeda é obrigatória")
    @Size(max = 10, message = "A sigla deve ter no máximo 10 caracteres")
    @Schema(description = "Sigla/ticker da moeda", example = "BTC", requiredMode = Schema.RequiredMode.REQUIRED)
    private String siglaMoeda;

    @Size(max = 100, message = "O código da carteira deve ter no máximo 100 caracteres")
    @Schema(description = "Código da moeda na carteira (opcional)", example = "bitcoin")
    private String codigoCarteira;

    @NotNull(message = "O id da carteira é obrigatório")
    @Schema(description = "Identificador da carteira associada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idCarteira;
}
