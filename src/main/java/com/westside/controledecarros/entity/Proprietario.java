package com.westside.controledecarros.entity;

import com.westside.controledecarros.enums.Bloco;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proprietarios")
@Getter
@Setter
@NoArgsConstructor
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String unidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Bloco bloco;

    @Column(nullable = false)
    private String telefone;

    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos = new ArrayList<>();
}