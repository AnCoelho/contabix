package com.projeto.contabix.service;

import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.data.entity.TipoUsuarioEntity;
import com.projeto.contabix.exception.NotFoundException;
import com.projeto.contabix.repository.UsuariosRepository;
import com.projeto.contabix.repository.TipoUsuarioRepository;
import com.projeto.contabix.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UsuariosService {

    private static final Logger logger = Logger.getLogger(UsuariosService.class.getName());

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public UsuariosDTO findById(Long id) {
        UsuariosEntity usuariosEntity = usuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ACESSO_USUARIOS_NOT_FOUND", "Acesso Usuários não encontrado."));

        return ModelMapperUtils.map(usuariosEntity, new UsuariosDTO());
    }

    public UsuariosDTO loginOrRegister(UsuariosDTO usuariosDTO) {
        String email = usuariosDTO.getEmail();
        String cnpj = usuariosDTO.getCnpj();
        String senha = usuariosDTO.getSenha();

        logger.info("Validando usuário: " + (email != null ? email : cnpj));

        if (email != null && !email.isEmpty()) {
            return handleLoginOrRegisterWithEmail(email, senha, usuariosDTO);
        } else if (cnpj != null && !cnpj.isEmpty()) {
            usuariosDTO.setCnpj(removeSpecialCharacters(cnpj)); // Remove caracteres especiais do CNPJ
            return handleLoginOrRegisterWithCnpj(cnpj, senha, usuariosDTO);
        } else {
            throw new IllegalArgumentException("Usuário inválido");
        }
    }

    
    private String removeSpecialCharacters(String value) {
        return value.replaceAll("[^\\d]", ""); // Remove todos os caracteres que não são dígitos
    }

    private UsuariosDTO handleLoginOrRegisterWithEmail(String email, String senha, UsuariosDTO usuariosDTO) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return loginWithEmail(email, senha);
        } else {
            if (usuariosDTO.getNome() == null || usuariosDTO.getNome().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
            }
            usuariosDTO.setAtivo(true);
            usuariosDTO.setDataCriacao(LocalDateTime.now());
            TipoUsuarioEntity tipoUsuario = getTipoUsuario(usuariosDTO.getIdTipoUsuario());
            UsuariosEntity newUsuario = ModelMapperUtils.map(usuariosDTO, new UsuariosEntity());
            newUsuario.setTipoUsuario(tipoUsuario);
            usuariosRepository.save(newUsuario);
            return ModelMapperUtils.map(newUsuario, new UsuariosDTO());
        }
    }
    
    private UsuariosDTO handleLoginOrRegisterWithCnpj(String cnpj, String senha, UsuariosDTO usuariosDTO) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByCnpj(cnpj);
        if (usuario.isPresent()) {
            return loginWithCnpj(cnpj, senha);
        } else {
            if (usuariosDTO.getNome() == null || usuariosDTO.getNome().isEmpty()) {
                throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
            }
            usuariosDTO.setAtivo(true);
            usuariosDTO.setDataCriacao(LocalDateTime.now());
            TipoUsuarioEntity tipoUsuario = getTipoUsuario(usuariosDTO.getIdTipoUsuario());
            UsuariosEntity newUsuario = ModelMapperUtils.map(usuariosDTO, new UsuariosEntity());
            newUsuario.setTipoUsuario(tipoUsuario);
            usuariosRepository.save(newUsuario);
            return ModelMapperUtils.map(newUsuario, new UsuariosDTO());
        }
    }

    private UsuariosDTO loginWithEmail(String email, String senha) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByEmailAndSenha(email, senha);
        if (usuario.isPresent()) {
            UsuariosDTO responseDTO = ModelMapperUtils.map(usuario.get(), new UsuariosDTO());
            return responseDTO;
        } else {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }
    }

    private UsuariosDTO loginWithCnpj(String cnpj, String senha) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByCnpjAndSenha(cnpj, senha);
        if (usuario.isPresent()) {
            UsuariosDTO responseDTO = ModelMapperUtils.map(usuario.get(), new UsuariosDTO());
            return responseDTO;
        } else {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }
    }

    private TipoUsuarioEntity getTipoUsuario(Long idTipoUsuario) {
        return tipoUsuarioRepository.findById(idTipoUsuario)
                .orElseThrow(() -> new NotFoundException("TIPO_USUARIO_NOT_FOUND", "Tipo de Usuário não encontrado."));
    }
}

