package com.projeto.contabix.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosDTO {
    private Long idUsuario;
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private TipoUsuarioDTO tipoUsuario;
    private LocalDateTime dataCriacao;
    private boolean ativo;
}