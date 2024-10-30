package com.projeto.contabix.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.projeto.contabix.model.entity.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Setter
    @Getter
    private String nome;
    @Setter
    @Getter
    private String cnpj;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String senha;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUsuario id_tipo_usuario;

    @Setter
    @Getter
    private LocalDate data_criacao;
    @Setter
    @Getter
    private boolean ativo;

    public Usuario() {
    }


}
