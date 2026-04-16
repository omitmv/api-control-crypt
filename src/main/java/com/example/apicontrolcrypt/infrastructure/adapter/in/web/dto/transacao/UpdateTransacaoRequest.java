package com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para atualização de uma transação")
public class UpdateTransacaoRequest {

    @Size(max = 100, message = "O código deve ter no máximo 100 caracteres")
    @Schema(description = "Novo código da transação", example = "TXN-20260415-001-UPD")
    private String codigoTransacao;

    @Schema(description = "Novo identificador da carteira associada", example = "1")
    private Integer idCarteira;

    @Schema(description = "Novo identificador da moeda associada", example = "1")
    private Integer idMoeda;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Nova data e hora da transação", example = "2026-04-15T10:30:00")
    private LocalDateTime dtTransacao;

    @Pattern(regexp = "[CV]", message = "O tipo deve ser 'C' (compra) ou 'V' (venda)")
    @Schema(description = "Novo tipo da transação: C=Compra, V=Venda", example = "V", allowableValues = {"C", "V"})
    private String tpTransacao;

    @Schema(description = "Nova cotação no momento da transação em centavos", example = "31000000")
    private Long cotacao;

    @Schema(description = "Nova quantidade da moeda transacionada", example = "50000000")
    private Long quantidade;

    @Schema(description = "Novo valor total da transação em centavos", example = "1550000000")
    private Long valor;
}
