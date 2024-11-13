package com.projeto.contabix.repository;

import com.projeto.contabix.data.entity.SolicitacoesEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SolicitacoesRepository extends JpaRepository<SolicitacoesEntity, Long> {
    List<SolicitacoesEntity> findByContador(UsuariosEntity contador);
}