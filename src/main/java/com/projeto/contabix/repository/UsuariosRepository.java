package com.projeto.contabix.repository;

import com.projeto.contabix.data.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {
    Optional<UsuariosEntity> findByEmail(String email);

    Optional<UsuariosEntity> findByCnpj(String cnpj);

    Optional<UsuariosEntity> findByEmailAndSenha(String email, String senha);

    Optional<UsuariosEntity> findByCnpjAndSenha(String cnpj, String senha);

    @Query("SELECT u FROM UsuariosEntity u WHERE u.tipoUsuario.idTipoUsuario = 1")
    List<UsuariosEntity> findContadores();

}