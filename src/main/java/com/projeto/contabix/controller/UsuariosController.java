package com.projeto.contabix.controller;

import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/register")
    public ResponseEntity<UsuariosDTO> register(@RequestBody UsuariosDTO usuariosDTO) {
        UsuariosDTO registeredUser = usuariosService.loginOrRegister(usuariosDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuariosDTO> login(@RequestBody UsuariosDTO usuariosDTO) {
        UsuariosDTO loggedInUser = usuariosService.loginOrRegister(usuariosDTO);
        return ResponseEntity.ok(loggedInUser);
    }
}
