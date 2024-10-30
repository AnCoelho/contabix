package com.projeto.contabix.service;

import com.projeto.contabix.model.dto.LoginRequest;
import com.projeto.contabix.model.dto.RegisterRequest;
import com.projeto.contabix.model.entity.Usuario;
import com.projeto.contabix.repository.TipoUsuarioRepository;
import com.projeto.contabix.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String jwtSecret = "contabix2024";

    public Usuario registrarUsuario(RegisterRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        if (request.getId_tipo_usuario() == 1) { // Cliente
            usuario.setCnpj(request.getCnpj());
        } else { // Contador
            usuario.setEmail(request.getEmail());
        }
        usuario.setId_tipo_usuario(tipoUsuarioRepository.findById(request.getId_tipo_usuario()).orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado")));
        usuario.setAtivo(true);
        usuario.setData_criacao(LocalDate.now());
        return usuarioRepository.save(usuario);
    }

    public String autenticarUsuario(LoginRequest request) {
        Usuario usuario;
        if (request.getLogin().contains("@")) { // Login por e-mail (Contador)
            usuario = usuarioRepository.findByEmail(request.getLogin()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } else { // Login por CNPJ (Cliente)
            usuario = usuarioRepository.findByCnpj(request.getLogin()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        }

        if (passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            return gerarToken(usuario);
        } else {
            throw new RuntimeException("Senha incorreta");
        }
    }

    private String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail() != null ? usuario.getEmail() : usuario.getCnpj())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token válido por 1 dia
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}

