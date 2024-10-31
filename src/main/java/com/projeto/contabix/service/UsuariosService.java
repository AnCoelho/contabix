package br.poo.unipac.uberlandia.ematriculasserver.service;

import br.poo.unipac.uberlandia.ematriculasserver.dao.UsuariosDisciplinasPeriodoDAO;
import br.poo.unipac.uberlandia.ematriculasserver.data.dto.EstatisticasDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.dto.MessagePeriodoDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.dto.UsuarioPeriodoDisciplinasDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.dto.UsuariosDTO;
import br.poo.unipac.uberlandia.ematriculasserver.data.entity.UsuariosEntity;
import br.poo.unipac.uberlandia.ematriculasserver.exception.BusinessException;
import br.poo.unipac.uberlandia.ematriculasserver.exception.NotFoundException;
import br.poo.unipac.uberlandia.ematriculasserver.repository.UsuariosRepository;
import br.poo.unipac.uberlandia.ematriculasserver.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UsuariosService {

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    UsuariosDisciplinasPeriodoDAO usuariosDisciplinasPeriodoDAO;

    public UsuariosDTO findById(Long id) {
        UsuariosEntity usuariosEntity = usuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ACESSO_USUARIOS_NOT_FOUND", "Acesso Usuários não encontrado."));

        return ModelMapperUtils.map(usuariosEntity, new UsuariosDTO());
    }

    public UsuariosDTO save(UsuariosEntity usuariosEntity) {

        if (usuariosEntity.getVrBolsa() == 0.0) {
            Double notaVestibular = usuariosEntity.getNotaVestibular();

            if (notaVestibular >= 60 && notaVestibular < 80) {
                usuariosEntity.setVrBolsa(0.3);
                usuariosEntity.setDsStatus("regular");
            } else if (notaVestibular >= 80 && notaVestibular < 95) {
                usuariosEntity.setVrBolsa(0.4);
                usuariosEntity.setDsStatus("regular");
            } else if (notaVestibular >= 95) {
                usuariosEntity.setVrBolsa(0.5);
                usuariosEntity.setDsStatus("regular");
            } else {
                usuariosEntity.setVrBolsa(0.0);
                usuariosEntity.setDsStatus("não matriculado");
            }

            usuariosEntity.setOidPeriodo(1L);
        }

        Long qtdeCpf = usuariosRepository.verificaDuplicidadeUsuario(usuariosEntity.getNrCpf());

        if (qtdeCpf.intValue() >= 1) {
            throw new BusinessException("USUARIO_EXISTENTE", "Usuário já registrado com este CPF! Faça o Login!.");
        }

        usuariosEntity.setDtNascimento(usuariosEntity.getDtNascimento());

        UsuariosEntity usuariosEntityNovo = usuariosRepository.save(usuariosEntity);
        return ModelMapperUtils.map(usuariosEntityNovo, new UsuariosDTO());
    }

    public UsuariosDTO login(String nrCpf, String senha) {

        UsuariosEntity usuario = usuariosRepository.findByNrCpfAndDsSenha(nrCpf, senha);

        if (usuario == null) {
            Long qtdeCpf = usuariosRepository.verificaDuplicidadeUsuario(nrCpf);
            if (qtdeCpf.intValue() >= 1) {
                throw new BusinessException("SENHA_INCORRETA", "Senha incorreta!");
            } else {
                throw new NotFoundException("USUARIO_INEXISTENTE", "Realize seu cadastro primeiro! Usuário inexistente!");
            }
        } else {
            return ModelMapperUtils.map(usuario, new UsuariosDTO());
        }
    }

    public MessagePeriodoDTO validaPeriodo(String nrCpf) {
        UsuariosEntity usuario = usuariosRepository.findByNrCpf(nrCpf);
        MessagePeriodoDTO message = new MessagePeriodoDTO();

        if (usuario == null) {
            throw new NotFoundException("USUARIO_MAO_ENCONTRADO", "Esse usuário não foi encontrado");
        }

        Long nrPeriodo = usuario.getOidPeriodo();


        if (nrPeriodo == null) {
            throw new BusinessException("USUARIO_SEM_MATRICULA", "O usuário não possui matrícula, portanto não está apto a fazer a rematrícula para o segundo período!");
        } else if (nrPeriodo == 1L) {
            usuario.setOidPeriodo(usuario.getOidPeriodo() + 1L);
            message.setOidMessage(1);
            message.setMessage("Parabéns, você está apto para cursar o segundo período, seja muito bem vindo !!");
        } else if (nrPeriodo == 2L) {
            message.setOidMessage(0);
            message.setMessage("Você já pertence ao segundo período, não é necessário fazer a rematrícula !!");
        }

        usuariosRepository.save(usuario);

        return message;
    }

    public UsuarioPeriodoDisciplinasDTO getAllDataUser(String nrCpf) {
        UsuariosEntity usuario = usuariosRepository.findByNrCpf(nrCpf);

        if (usuario == null) {
            throw new NotFoundException("USUARIO_MAO_ENCONTRADO", "Esse usuário não foi encontrado");
        }

        return usuariosDisciplinasPeriodoDAO.retornaListaUsuariosDisciplinasPeriodo(nrCpf).get(0);

    }

    public UsuariosDTO update(UsuariosDTO usuarioDTO, String cpf) {
        UsuariosEntity usuarioRec = usuariosRepository.findByNrCpf(cpf);

        if (usuarioRec != null) {
            if (usuarioDTO.getNome() == null || usuarioDTO.getNome().isEmpty()) {
                usuarioDTO.setNome(usuarioRec.getNome());
            }

            if (usuarioDTO.getDsSenha() == null || usuarioDTO.getDsSenha().isEmpty()) {
                usuarioDTO.setDsSenha(usuarioRec.getDsSenha());
            }

            usuarioRec.setNome(usuarioDTO.getNome());
            usuarioRec.setDsSenha(usuarioDTO.getDsSenha());

            usuarioRec = usuariosRepository.save(usuarioRec);


            return ModelMapperUtils.map(usuarioRec, new UsuariosDTO());
        } else {
            throw new EntityNotFoundException("Usuário não encontrado para o CPF: " + cpf);
        }
    }

    public void deleteFisico(Long oidUsuario){
        usuariosRepository.deleteById(oidUsuario);
    }

    public EstatisticasDTO calcularEstatisticasNotasVestibular() {
        List<UsuariosEntity> usuarios = usuariosRepository.findAll();
        int totalAlunos = usuarios.size();

        int excelente = 0;
        int bom = 0;
        int mediano = 0;
        int ruim = 0;

        for (UsuariosEntity usuario : usuarios) {
            double nota = usuario.getNotaVestibular();

            if (nota >= 95) {
                excelente++;
            } else if (nota >= 80 && nota < 95) {
                bom++;
            } else if (nota >= 60 && nota < 80) {
                mediano++;
            } else {
                ruim++;
            }
        }

        long excelentePercentual = Math.round((double) excelente / totalAlunos * 100);
        long bomPercentual = Math.round((double) bom / totalAlunos * 100);
        long medianoPercentual = Math.round((double) mediano / totalAlunos * 100);
        long ruimPercentual = Math.round((double) ruim / totalAlunos * 100);

        EstatisticasDTO estatisticas = new EstatisticasDTO();
        estatisticas.setExcelente(excelentePercentual);
        estatisticas.setBom(bomPercentual);
        estatisticas.setMediano(medianoPercentual);
        estatisticas.setRuim(ruimPercentual);

        return estatisticas;
    }
}
