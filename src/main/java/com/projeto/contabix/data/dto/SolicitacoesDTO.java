package com.projeto.contabix.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacoesDTO {
    private Long idSolicitacao;
    private UsuariosDTO cliente;
    private UsuariosDTO contador;
    private String servico;
    private String urgencia;
    private LocalDateTime prazo;
    private String assunto;
    private String descricao;
    private String status;
    private LocalDateTime dataAbertura;
}