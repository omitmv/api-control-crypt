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
public class Carteira {

    private Integer idCarteira;
    private String dsCarteira;
    private LocalDateTime dtCadastro;
    private LocalDateTime dtUltAtualizacao;
}
