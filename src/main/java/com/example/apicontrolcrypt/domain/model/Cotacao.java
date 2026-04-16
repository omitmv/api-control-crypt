package com.example.apicontrolcrypt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cotacao {

    private Integer idCotacao;
    private Integer idCarteira;
    private Integer idMoeda;
    private LocalDateTime dtUltAtualizacao;
    private Long cotacao;
    private BigDecimal perc24h;
}
