package com.projeto.contabix.data.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDTO {
    private Long idEvento;
    private Long usuarioId;
    private Long solicitacaoId;
    private Long solicitanteId;
    private Long destinatarioId;
    private String descricao;
    private Date dataEvento;
    private String tipoEvento;
    private boolean notificado;
}
