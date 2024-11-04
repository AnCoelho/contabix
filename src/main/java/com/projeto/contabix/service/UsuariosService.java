package com.projeto.contabix.service;

import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.exception.NotFoundException;
import com.projeto.contabix.repository.UsuariosRepository;
import com.projeto.contabix.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public UsuariosDTO findById(Long id) {
        UsuariosEntity usuariosEntity = usuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ACESSO_USUARIOS_NOT_FOUND", "Acesso Usuários não encontrado."));

        return ModelMapperUtils.map(usuariosEntity, new UsuariosDTO());
    }

    public UsuariosDTO loginOrRegister(UsuariosDTO usuariosDTO) {
        String usuario = usuariosDTO.getUsuario();
        String senha = usuariosDTO.getSenha();

        if (isEmail(usuario)) {
            usuariosDTO.setEmail(usuario);
            return handleLoginOrRegisterWithEmail(usuario, senha, usuariosDTO);
        } else if (isCnpj(usuario)) {
            usuariosDTO.setCnpj(usuario);
            return handleLoginOrRegisterWithCnpj(usuario, senha, usuariosDTO);
        } else {
            throw new IllegalArgumentException("Usuário inválido");
        }
    }

    private boolean isEmail(String usuario) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return usuario.matches(emailRegex);
    }

    private boolean isCnpj(String usuario) {
        String cnpjRegex = "\\d{14}";
        return usuario.matches(cnpjRegex);
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
            //usuariosDTO.setIdTipoUsuario(1L);
            UsuariosEntity newUsuario = ModelMapperUtils.map(usuariosDTO, new UsuariosEntity());
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
            //usuariosDTO.setIdTipoUsuario(2L);
            UsuariosEntity newUsuario = ModelMapperUtils.map(usuariosDTO, new UsuariosEntity());
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
}