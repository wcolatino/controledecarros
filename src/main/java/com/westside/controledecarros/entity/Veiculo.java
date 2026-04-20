package com.westside.controledecarros.entity;

import com.westside.controledecarros.enums.StatusVeiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private Integer vaga;

    @Column(nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private LocalDate dataExpiracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVeiculo status;

    @ManyToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Proprietario proprietario;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDate.now();
        this.dataExpiracao = this.dataCadastro.plusDays(15);
        this.status = StatusVeiculo.ATIVO;
    }
}
