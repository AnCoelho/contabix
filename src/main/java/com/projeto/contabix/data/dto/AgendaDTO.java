package com.projeto.contabix.data.dto;

import java.util.Date;

import com.projeto.contabix.data.entity.SolicitacoesEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDTO {
    private Long idEvento;
    private UsuariosEntity usuario;
    private SolicitacoesEntity solicitacao;
    private UsuariosEntity solicitante;
    private UsuariosEntity destinatario;
    private String descricao;
    private Date dataEvento;
    private String tipoEvento;
    private boolean notificado;
}
