package com.projeto.contabix.data.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@ApiModel(description = "TABELA TIPO_USUARIO.")
@Data
@Entity
@Table(name = "TIPO_USUARIO", schema = "PUBLIC")
public class TipoUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_USUARIO")
    private Long idTipoUsuario;
    
    @Column(name = "DESCRICAO", length = 100)
    private String descricao; 

    
}
