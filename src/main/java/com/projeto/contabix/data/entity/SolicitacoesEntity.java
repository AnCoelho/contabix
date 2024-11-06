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
    private Long idSolicitacao;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private UsuariosEntity idCliente;

    @ManyToOne
    @JoinColumn(name = "ID_CONTADOR", nullable = true)
    private UsuariosEntity idContador;

    private String servico;
    private String urgencia;
    private Timestamp prazo;
    private String assunto;
    private String descricao;
    private String status;

    @Column(name = "DATA_ABERTURA")
    private LocalDateTime dataAbertura;
}