package com.example.apicontrolcrypt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    private Integer idTransacao;
    private String codigoTransacao;
    private Integer idCarteira;
    private Integer idMoeda;
    private LocalDateTime dtTransacao;
    private String tpTransacao;
    private Long cotacao;
    private Long quantidade;
    private Long valor;
}
