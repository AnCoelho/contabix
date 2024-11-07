package com.projeto.contabix.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private UsuariosEntity idCliente;

    @ManyToOne
    @JoinColumn(name = "ID_CONTADOR", nullable = true)
    private UsuariosEntity idContador;


    @Column(name = "SERVICO")
    private String servico;

    @Column(name = "URGENCIA")
    private String urgencia;

    @Column(name = "PRAZO")
    private Timestamp prazo;

    @Column(name = "ASSUNTO")
    private String assunto;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DATA_ABERTURA")
    private LocalDateTime dataAbertura;
}