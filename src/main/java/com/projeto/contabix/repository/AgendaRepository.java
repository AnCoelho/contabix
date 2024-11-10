package com.projeto.contabix.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.contabix.data.entity.AgendaEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {
        @Query("SELECT a FROM AgendaEntity a WHERE EXTRACT(MONTH FROM a.dataEvento) = :mes AND EXTRACT(YEAR FROM a.dataEvento) = :ano AND a.usuario = :usuario")
        List<AgendaEntity> findAllByMonthAndYearAndUsuario(
                        @Param("mes") Long mes,
                        @Param("ano") Long ano,
                        @Param("usuario") UsuariosEntity usuariosEntity);

        @Query("SELECT a FROM AgendaEntity a WHERE a.dataEvento BETWEEN :startOfDay AND :endOfDay AND a.usuario = :usuario")
        List<AgendaEntity> findAllByDateRangeAndUsuario(
                        @Param("startOfDay") LocalDateTime startOfDay,
                        @Param("endOfDay") LocalDateTime endOfDay,
                        @Param("usuario") UsuariosEntity usuario);

        List<AgendaEntity> findAllByNotificadoAndDestinatario(boolean notificado, UsuariosEntity usuariosEntity);
}
