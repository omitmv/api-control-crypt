package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para criação de uma carteira")
public class CreateCarteiraRequest {

    @NotBlank(message = "A descrição da carteira é obrigatória")
    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
    @Schema(description = "Descrição/nome da carteira", example = "Carteira Binance", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dsCarteira;
}
