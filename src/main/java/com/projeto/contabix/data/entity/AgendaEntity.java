package com.projeto.contabix.data.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AGENDA")
public class AgendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENTO")
    private Long idEvento;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UsuariosEntity usuario;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DATA_EVENTO")
    private Date dataEvento;

    @Column(name = "TIPO_EVENTO")
    private String tipoEvento;

    @Column(name = "NOTIFICADO")
    private boolean notificado;
}
