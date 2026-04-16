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
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbMoeda")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoedaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moeda")
    private Integer idMoeda;

    @Column(name = "ds_moeda", nullable = false, unique = true, length = 100)
    private String dsMoeda;

    @Column(name = "sigla_moeda", nullable = false, unique = true, length = 10)
    private String siglaMoeda;

    @Column(name = "codigo_carteira", length = 100)
    private String codigoCarteira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carteira", nullable = false)
    private CarteiraEntity carteira;

    @CreationTimestamp
    @Column(name = "dt_cadastro", nullable = false, updatable = false)
    private LocalDateTime dtCadastro;
}
