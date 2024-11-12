package com.projeto.contabix.controller;

import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.service.UsuariosService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuariosDTO> findById(@PathVariable Long id) {
        UsuariosDTO usuario = usuariosService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/getContadores")
    public List<UsuariosDTO> findContadores() {
        return usuariosService.findContadores();
    }

    @PostMapping("/loginOrRegister")
    public ResponseEntity<UsuariosDTO> loginOrRegister(@RequestBody UsuariosDTO usuariosDTO) {
        UsuariosDTO usuario = usuariosService.loginOrRegister(usuariosDTO);
        return ResponseEntity.ok(usuario);
    }
}