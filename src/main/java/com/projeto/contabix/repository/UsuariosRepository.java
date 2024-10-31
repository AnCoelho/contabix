package br.poo.unipac.uberlandia.ematriculasserver.repository;

import br.poo.unipac.uberlandia.ematriculasserver.data.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {
    @Query(value = "SELECT COUNT(1) FROM public.Usuarios WHERE NR_CPF = :nrCpf", nativeQuery = true)
    Long verificaDuplicidadeUsuario(@Param("nrCpf") String nrCpf);

    UsuariosEntity findByNrCpfAndDsSenha(String nrCpf, String senha);

    UsuariosEntity findByNrCpf(String nrCpf);
}
