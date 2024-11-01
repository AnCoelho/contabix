package com.projeto.contabix.service;

import com.projeto.contabix.dao.UsuariosDisciplinasPeriodoDAO;
import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.data.entity.TipoUsuarioEntity;
import com.projeto.contabix.exception.BusinessException;
import com.projeto.contabix.exception.NotFoundException;
import com.projeto.contabix.repository.UsuariosRepository;
import com.projeto.contabix.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
        if (isEmail(usuariosDTO.getEmail())) {
            return loginWithEmail(usuariosDTO.getEmail(), usuariosDTO.getSenha());
        } else if (isCnpj(usuariosDTO.getCnpj())) {
            return loginWithCnpj(usuariosDTO.getCnpj(), usuariosDTO.getSenha());
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

    private UsuariosDTO loginWithEmail(String email, String senha) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByEmailAndSenha(email, senha);
        if (usuario.isPresent()) {
            return new UsuariosDTO(usuario.get());
        } else {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }
    }

    private UsuariosDTO loginWithCnpj(String cnpj, String senha) {
        Optional<UsuariosEntity> usuario = usuariosRepository.findByCnpjAndSenha(cnpj, senha);
        if (usuario.isPresent()) {
            return new UsuariosDTO(usuario.get());
        } else {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }
    }







        boolean isEmail = usuariosDTO.getIdTipoUsuario() == 1;
        boolean isCnpj = usuariosDTO.getIdTipoUsuario() == 2;

        if (usuariosDTO.getNome() != null && usuariosDTO.getSenha() != null) {
            // Trata como cadastro
            if (isEmail && usuariosDTO.getEmail() != null) {
                // Cadastro para usuário com email
                UsuariosEntity usuarioEntity = new UsuariosEntity(
                    usuariosDTO.getNome(),
                    usuariosDTO.getEmail(),
                    usuariosDTO.getSenha(),
                    usuariosDTO.getIdTipoUsuario()
                );
                usuariosRepository.save(usuarioEntity);
                return new UsuariosDTO(usuarioEntity);
            } else if (isCnpj && usuariosDTO.getCnpj() != null) {
                // Cadastro para usuário com CNPJ
                UsuariosEntity usuariosEntity = new UsuariosEntity(
                    usuariosDTO.getNome(),
                    usuariosDTO.getCnpj(),
                    usuariosDTO.getSenha(),
                    usuariosDTO.getIdTipoUsuario()
                );
                usuariosRepository.save(usuarioEntity);
                return new UsuariosDTO(usuariosEntity);
            } else {
                throw new IllegalArgumentException("Dados inválidos para cadastro");
            }
        } else {
            // Trata como login
            Optional<UsuariosEntity> usuario;
            if (isEmail) {
                usuario = usuariosRepository.findByEmailAndSenha(usuariosDTO.getEmail(), usuariosDTO.getSenha());
            } else if (isCnpj) {
                usuario = usuariosRepository.findByCnpjAndSenha(usuariosDTO.getCnpj(), usuariosDTO.getSenha());
            } else {
                throw new IllegalArgumentException("Tipo de usuário inválido");
            }

            if (usuario.isPresent()) {
                return new UsuariosDTO(usuario.get());
            } else {
                throw new IllegalArgumentException("Usuário ou senha incorretos");
            }
        }
    }
}