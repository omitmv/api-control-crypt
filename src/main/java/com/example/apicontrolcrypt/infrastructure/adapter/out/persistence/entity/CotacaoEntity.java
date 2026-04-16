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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbCotacao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacao")
    private Integer idCotacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carteira", nullable = false)
    private CarteiraEntity carteira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moeda", nullable = false)
    private MoedaEntity moeda;

    @Column(name = "dt_ult_atualizacao")
    private LocalDateTime dtUltAtualizacao;

    @Column(name = "cotacao")
    private Long cotacao;

    @Column(name = "perc_24h", precision = 10, scale = 2)
    private BigDecimal perc24h;
}
