package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para atualização de uma carteira")
public class UpdateCarteiraRequest {

    @Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
    @Schema(description = "Nova descrição/nome da carteira", example = "Carteira Coinbase")
    private String dsCarteira;
}
