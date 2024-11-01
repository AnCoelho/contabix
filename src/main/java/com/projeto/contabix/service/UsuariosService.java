package com.projeto.contabix.service;

import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.exception.NotFoundException;
import com.projeto.contabix.repository.UsuariosRepository;
import com.projeto.contabix.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        boolean isEmail = !usuariosDTO.isCliente();
        boolean isCnpj = usuariosDTO.isCliente();

        if (usuariosDTO.getNome() != null && usuariosDTO.getSenha() != null) {
            // Handle registration
            if (isEmail && usuariosDTO.getEmail() != null) {
                return registerWithEmail(usuariosDTO);
            } else if (isCnpj && usuariosDTO.getCnpj() != null) {
                return registerWithCnpj(usuariosDTO);
            } else {
                throw new IllegalArgumentException("Dados inválidos para cadastro");
            }
        } else {
            // Handle login
            if (isEmail) {
                return loginWithEmail(usuariosDTO.getEmail(), usuariosDTO.getSenha());
            } else {
                return loginWithCnpj(usuariosDTO.getCnpj(), usuariosDTO.getSenha());
            }
        }
    }

    private UsuariosDTO registerWithEmail(UsuariosDTO usuariosDTO) {
        UsuariosEntity usuarioEntity = new UsuariosEntity(
                usuariosDTO.getNome(),
                usuariosDTO.getEmail(),
                usuariosDTO.getSenha(),
                false
        );
        usuariosRepository.save(usuarioEntity);
        return ModelMapperUtils.map(usuarioEntity, new UsuariosDTO());
    }

    private UsuariosDTO registerWithCnpj(UsuariosDTO usuariosDTO) {
        UsuariosEntity usuarioEntity = new UsuariosEntity(
                usuariosDTO.getNome(),
                usuariosDTO.getCnpj(),
                usuariosDTO.getSenha(),
                true
        );
        usuariosRepository.save(usuarioEntity);
        return ModelMapperUtils.map(usuarioEntity, new UsuariosDTO());
    }

    private UsuariosDTO loginWithEmail(String email, String senha) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByEmailAndSenha(email, senha);
        if (usuario.isPresent()) {
            return ModelMapperUtils.map(usuario.get(), new UsuariosDTO());
        } else {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }
    }

    private UsuariosDTO loginWithCnpj(String cnpj, String senha) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByCnpjAndSenha(cnpj, senha);
        if (usuario.isPresent()) {
            return ModelMapperUtils.map(usuario.get(), new UsuariosDTO());
        } else {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }
    }
}