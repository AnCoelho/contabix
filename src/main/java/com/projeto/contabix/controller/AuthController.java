package com.projeto.contabix.controller;

import com.projeto.contabix.model.dto.LoginRequest;
import com.projeto.contabix.model.dto.RegisterRequest;
import com.projeto.contabix.model.entity.Usuario;
import com.projeto.contabix.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RegisterRequest request) {
        Usuario usuario = authService.registrarUsuario(request);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.autenticarUsuario(request);
        return ResponseEntity.ok(token);
    }


}
