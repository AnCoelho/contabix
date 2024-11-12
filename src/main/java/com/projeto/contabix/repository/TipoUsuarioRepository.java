package com.projeto.contabix.repository;

import com.projeto.contabix.data.entity.TipoUsuarioEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {
    Optional<TipoUsuarioEntity> findByDescricao(String descricao);
}