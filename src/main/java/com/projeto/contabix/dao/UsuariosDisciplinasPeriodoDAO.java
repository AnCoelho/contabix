package br.poo.unipac.uberlandia.ematriculasserver.dao;

import br.poo.unipac.uberlandia.ematriculasserver.data.dto.UsuarioPeriodoDisciplinasDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UsuariosDisciplinasPeriodoDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<UsuarioPeriodoDisciplinasDTO> retornaListaUsuariosDisciplinasPeriodo(String nrCpf) {
        Map<String, Object> parameters = new HashMap<>();
        Map<String, UsuarioPeriodoDisciplinasDTO> tempMap = new HashMap<>();
        parameters.put("nrCpf", nrCpf);

        String sql = "SELECT usuarios.oid_usuarios as oid_usuario, " +
                "usuarios.nome as nm_usuario, " +
                "usuarios.nr_cpf as cpf_usuario, " +
                "usuarios.dt_nascimento as dt_nascimento, " +
                "usuarios.nm_responsavel as nm_responsavel, " +
                "usuarios.nota_vestibular as nota, " +
                "usuarios.ds_status as status, " +
                "usuarios.oid_periodo, " +
                "usuarios.vr_bolsa as bolsa, " +
                "usuarios.ds_senha as senha, " +
                "periodo.nome as ds_periodo, " +
                "disciplinas.nome as ds_disciplinas " +
                "FROM usuarios " +
                "left join periodos periodo on periodo.oid_periodos = usuarios.oid_periodo " +
                "left join disciplinas disciplinas on disciplinas.oid_periodo = periodo.oid_periodos " +
                "where usuarios.nr_cpf = :nrCpf ";

        namedParameterJdbcTemplate.query(sql, parameters, (rs) -> {
            String key = rs.getLong("oid_usuario") + "_" + rs.getLong("oid_periodo");
            if (!tempMap.containsKey(key)) {
                UsuarioPeriodoDisciplinasDTO dto = new UsuarioPeriodoDisciplinasDTO();
                dto.setOidUsuario(rs.getLong("oid_usuario"));
                dto.setNmUsuario(rs.getString("nm_usuario"));
                dto.setCpfUsuario(rs.getString("cpf_usuario"));
                dto.setDtNascimento(rs.getDate("dt_nascimento"));
                dto.setNmResponsavel(rs.getString("nm_responsavel"));
                dto.setNotaVestibular(rs.getDouble("nota"));
                dto.setDsStatus(rs.getString("status"));
                dto.setOidPeriodo(rs.getLong("oid_periodo"));
                dto.setVrBolsa(rs.getDouble("bolsa"));
                dto.setDsPeriodo(rs.getString("ds_periodo"));
                dto.setDisciplinas(new ArrayList<>());
                dto.setDsSenha(rs.getString("senha"));
                tempMap.put(key, dto);
            }
            UsuarioPeriodoDisciplinasDTO dto = tempMap.get(key);
            dto.getDisciplinas().add(rs.getString("ds_disciplinas"));
        });

        return new ArrayList<>(tempMap.values());
    }
}
