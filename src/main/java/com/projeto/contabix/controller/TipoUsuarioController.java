package com.projeto.contabix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.contabix.data.dto.TipoUsuarioDTO;
import com.projeto.contabix.service.TipoUsuarioService;

import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/tiposUsuario")
public class TipoUsuarioController {
    @Autowired
    private TipoUsuarioService tipoUsuarioService;


    @GetMapping("/getAll")
    @ApiOperation(value = "Busca todos tipos de usuário")   
    public List<TipoUsuarioDTO> getAllTiposUsuario() {
        return tipoUsuarioService.getAllTiposUsuarios();
    }
}
