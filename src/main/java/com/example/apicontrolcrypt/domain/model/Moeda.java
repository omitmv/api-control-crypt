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
public class Moeda {

    private Integer idMoeda;
    private String dsMoeda;
    private String siglaMoeda;
    private String codigoCarteira;
    private Integer idCarteira;
    private LocalDateTime dtCadastro;
}
