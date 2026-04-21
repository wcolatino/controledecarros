package com.westside.controledecarros.entity;

import com.westside.controledecarros.enums.Acao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Entidade de audit log — registra todas as ações realizadas no sistema
@Entity
@Table(name = "logs")
@Getter
@Setter
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ação realizada: CREATE, UPDATE, DELETE, READ
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Acao acao;

    // Nome da entidade afetada: Proprietario, Veiculo
    @Column(nullable = false)
    private String entidade;

    // ID do registro afetado
    @Column(nullable = false)
    private Long entidadeId;

    // Login do usuário que realizou a ação
    @Column(nullable = false)
    private String usuarioLogin;

    // Data e hora da ação
    @Column(nullable = false)
    private LocalDateTime dataHora;

    // Detalhes da ação em formato JSON (dados criados, antes/depois da alteração, dados deletados)
    @Column(columnDefinition = "TEXT")
    private String detalhes;

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
    }
}