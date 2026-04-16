package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para criação de uma transação")
public class CreateTransacaoRequest {

    @NotBlank(message = "O código da transação é obrigatório")
    @Size(max = 100, message = "O código deve ter no máximo 100 caracteres")
    @Schema(description = "Código único identificador da transação", example = "TXN-20260415-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoTransacao;

    @NotNull(message = "O id da carteira é obrigatório")
    @Schema(description = "Identificador da carteira associada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idCarteira;

    @NotNull(message = "O id da moeda é obrigatório")
    @Schema(description = "Identificador da moeda associada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idMoeda;

    @NotNull(message = "A data da transação é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data e hora da transação", example = "2026-04-15T10:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime dtTransacao;

    @NotBlank(message = "O tipo da transação é obrigatório")
    @Pattern(regexp = "[CV]", message = "O tipo deve ser 'C' (compra) ou 'V' (venda)")
    @Schema(description = "Tipo da transação: C=Compra, V=Venda", example = "C", allowableValues = {"C", "V"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String tpTransacao;

    @NotNull(message = "O valor da cotação é obrigatório")
    @Schema(description = "Cotação no momento da transação em centavos", example = "30000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long cotacao;

    @NotNull(message = "A quantidade é obrigatória")
    @Schema(description = "Quantidade da moeda transacionada (em unidades mínimas)", example = "100000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long quantidade;

    @NotNull(message = "O valor total é obrigatório")
    @Schema(description = "Valor total da transação em centavos", example = "3000000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long valor;
}
