package com.projeto.contabix.repository;

import com.projeto.contabix.data.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {

    @Query(value = "SELECT COUNT(1) FROM public.USUARIOS WHERE CPF = :cpf", nativeQuery = true)
    Long verificaDuplicidadeUsuario(@Param("cpf") String cpf);

    @Query(value = "SELECT COUNT(1) FROM public.USUARIOS WHERE CNPJ = :cnpj", nativeQuery = true)
    Long verificaDuplicidadeUsuarios(@Param("cnpj") String cnpj);

    Optional<UsuariosEntity> findByEmailAndSenha(String email, String senha);
    Optional<UsuariosEntity> findByCnpjAndSenha(String cnpj, String senha);

    Optional<UsuariosEntity> findByEmail(String email);
    Optional<UsuariosEntity> findByCnpj(String cnpj);
}