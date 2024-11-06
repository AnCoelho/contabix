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
    private Long idCliente;
    private Long idContador;
    private String servico;
    private String urgencia;
    private String cnpj;
    private String email;
    private String senha;
    private boolean ativo;
    private LocalDateTime dataCriacao;
}