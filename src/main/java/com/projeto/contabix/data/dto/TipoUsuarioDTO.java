package com.projeto.contabix.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuarioDTO {

    private Long idTipoUsuario;
    private String descricao;
}