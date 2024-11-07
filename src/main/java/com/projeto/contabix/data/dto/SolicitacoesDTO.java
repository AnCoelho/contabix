package com.projeto.contabix.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacoesDTO {
    private Long idSolicitacao;
    private Long idCliente;
    private Long idContador;
    private String servico;
    private String urgencia;
    private Timestamp prazo;
    private String assunto;
    private String descricao;
    private String status;
    private LocalDateTime dataAbertura;
}