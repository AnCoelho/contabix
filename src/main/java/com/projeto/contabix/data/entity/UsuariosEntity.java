package com.projeto.contabix.data.entity;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import jakarta.persistence.*;

@ApiModel(description = "TABELA USUARIOS.")
@Data
@Entity
@Table(name = "USUARIOS", schema = "PUBLIC")
public class UsuariosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOME", length = 100)
    private String nome;

    @Column(name = "CPF", length = 14)
    private String cnpj;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "SENHA", length = 100)
    private String senha;

    @Column(name = "ATIVO")
    private boolean ativo;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
