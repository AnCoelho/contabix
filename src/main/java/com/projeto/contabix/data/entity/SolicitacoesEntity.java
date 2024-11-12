package com.projeto.contabix.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "SOLICITACOES")
public class SolicitacoesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOLICITACAO")
    private Long idSolicitacao;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private UsuariosEntity cliente;

    @ManyToOne
    @JoinColumn(name = "ID_CONTADOR", nullable = true)
    private UsuariosEntity contador;

    @Column(name = "SERVICO", length = 50, nullable = false)
    private String servico;

    @Column(name = "URGENCIA", length = 10)
    private String urgencia;

    @Column(name = "PRAZO", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime prazo;

    @Column(name = "ASSUNTO", length = 255, nullable = false)
    private String assunto;

    @Column(name = "DESCRICAO", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;

    @Column(name = "DATA_ABERTURA", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataAbertura;
}