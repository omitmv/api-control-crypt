package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbTransacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transacao")
    private Integer idTransacao;

    @Column(name = "codigo_transacao", nullable = false, length = 100)
    private String codigoTransacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carteira", nullable = false)
    private CarteiraEntity carteira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moeda", nullable = false)
    private MoedaEntity moeda;

    @Column(name = "dt_transacao", nullable = false)
    private LocalDateTime dtTransacao;

    @Column(name = "tp_transacao", nullable = false, length = 1)
    private String tpTransacao;

    @Column(name = "cotacao", nullable = false)
    private Long cotacao;

    @Column(name = "quantidade", nullable = false)
    private Long quantidade;

    @Column(name = "valor", nullable = false)
    private Long valor;
}
