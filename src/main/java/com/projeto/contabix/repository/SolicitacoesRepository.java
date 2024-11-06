package com.projeto.contabix.repository;

import com.projeto.contabix.data.entity.SolicitacoesEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacoesRepository extends JpaRepository<SolicitacoesEntity, Long> {
    List<SolicitacoesEntity> findByIdCliente(UsuariosEntity idCliente);
    List<SolicitacoesEntity> findByIdContador(UsuariosEntity idContador);
    List<SolicitacoesEntity> findAllByOrderByStatusAsc();
}