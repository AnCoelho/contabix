package com.projeto.contabix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projeto.contabix.data.entity.AgendaEntity;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {
    @Query("SELECT a FROM AgendaEntity a WHERE EXTRACT(MONTH FROM a.dataEvento) = :mes AND EXTRACT(YEAR FROM a.dataEvento) = :ano")
    List<AgendaEntity> findAllByMonthAndYear(@Param("mes") Long mes, @Param("ano") Long ano);
}
