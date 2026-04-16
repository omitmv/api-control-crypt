package com.example.apicontrolcrypt.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbCarteira")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carteira")
    private Integer idCarteira;

    @Column(name = "ds_carteira", nullable = false, unique = true, length = 100)
    private String dsCarteira;

    @CreationTimestamp
    @Column(name = "dt_cadastro", nullable = false, updatable = false)
    private LocalDateTime dtCadastro;

    @UpdateTimestamp
    @Column(name = "dt_ult_atualizacao")
    private LocalDateTime dtUltAtualizacao;
}
