package com.projeto.contabix.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosDTO extends TipoUsuarioDTO {
    private Long idUsuario;
    private String nome;
    private String usuario;
    private String cnpj;
    private String email;
    private String senha;
    private Long idTipoUsuario; 
    private boolean ativo;
    private LocalDateTime dataCriacao;
}