package com.projeto.contabix.service;

import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.data.entity.TipoUsuarioEntity;
import com.projeto.contabix.exception.NotFoundException;
import com.projeto.contabix.exception.BusinessException;
import com.projeto.contabix.repository.UsuariosRepository;
import com.projeto.contabix.repository.TipoUsuarioRepository;
import com.projeto.contabix.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public UsuariosDTO findById(Long id) {
        UsuariosEntity usuariosEntity = usuariosRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("ACESSO_USUARIOS_NOT_FOUND", "Acesso Usuários não encontrado."));

        return ModelMapperUtils.map(usuariosEntity, new UsuariosDTO());
    }

    public UsuariosDTO loginOrRegister(UsuariosDTO usuariosDTO) {
        if (usuariosDTO.getNome() != null && usuariosDTO.getTipoUsuario() != null && usuariosDTO.getSenha() != null) {
            UsuariosEntity usuariosEntity = ModelMapperUtils.map(usuariosDTO, new UsuariosEntity());
            LocalDateTime localDateTime = LocalDateTime.now();
            usuariosEntity.setAtivo(true);
            usuariosEntity.setDataCriacao(localDateTime);
            usuariosRepository.save(usuariosEntity);

            return ModelMapperUtils.map(usuariosEntity, new UsuariosDTO());
        } else {
            if (usuariosDTO.getCnpj() != null) {
                Optional<UsuariosEntity> user = usuariosRepository.findByCnpj(usuariosDTO.getCnpj());
                if (!user.isPresent()) {
                    throw new BusinessException("CNPJ_NAO_ENCONTRADO",
                            "Esse usuário não existe, por favor, faça o cadastro!");
                }

                if (!usuariosDTO.getSenha().equals(user.get().getSenha())) {
                    throw new BusinessException("SENHA_INCORRETA", "A senha está incorreta!");
                }

                return ModelMapperUtils.map(user.get(), new UsuariosDTO());
            } else if (usuariosDTO.getEmail() != null) {
                Optional<UsuariosEntity> user = usuariosRepository.findByEmail(usuariosDTO.getEmail());
                if (!user.isPresent()) {
                    throw new BusinessException("EMAIL_NAO_ENCONTRADO",
                            "Esse usuário não existe, por favor, faça o cadastro!");
                }

                if (!usuariosDTO.getSenha().equals(user.get().getSenha())) {
                    throw new BusinessException("SENHA_INCORRETA", "A senha está incorreta!");
                }

                return ModelMapperUtils.map(user.get(), new UsuariosDTO());
            }
        }
        throw new BusinessException("ERRO_INESPERADO", "Houve um erro inesperado!");
    }

    private TipoUsuarioEntity getTipoUsuario(Long idTipoUsuario) {
        return tipoUsuarioRepository.findById(idTipoUsuario)
                .orElseThrow(() -> new NotFoundException("TIPO_USUARIO_NOT_FOUND", "Tipo de Usuário não encontrado."));
    }
}
