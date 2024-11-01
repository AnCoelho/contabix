package com.projeto.contabix.repository;

import com.projeto.contabix.data.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {
    @Query(value = "SELECT COUNT(1) FROM public.Usuarios WHERE NR_CPF = :nrCpf", nativeQuery = true)
    Long verificaDuplicidadeUsuario(@Param("nrCpf") String nrCpf);

    @Query(value = "SELECT COUNT(1) FROM public.Usuarios WHERE NR_CNPJ = :nrCnpj", nativeQuery = true)
    Long verificaDuplicidadeUsuarios(@Param("nrCnpj") String nrCnpj);

    UsuariosEntity findByNrCpfAndDsSenha(String nrCpf, String senha);
    UsuariosEntity findByNrCpnpjAndDsSenha(String nrCnpj, String senha);

    UsuariosEntity findByNrCpf(String nrCpf);
    UsuariosEntity findByNrCnpj(String nrCnpj);

   
        Optional<UsuariosEntity> findByEmail(String email);
        Optional<UsuariosEntity> findByCnpj(String cnpj);
        Optional<UsuariosEntity> findByEmailAndSenha(String email, String senha);
        Optional<UsuariosEntity> findByCnpjAndSenha(String cnpj, String senha);

}
