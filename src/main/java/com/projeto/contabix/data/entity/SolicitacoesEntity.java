package com.projeto.contabix.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "solicitacoes")
public class SolicitacoesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitacao;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private UsuariosEntity idCliente;

    @ManyToOne
    @JoinColumn(name = "id_contador", nullable = true)
    private UsuariosEntity idContador;

    private String servico;
    private String urgencia;
    private String prazo;
    private String assunto;
    private String descricao;
    private String status;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;
}