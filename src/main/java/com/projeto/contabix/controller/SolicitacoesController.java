package com.projeto.contabix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.projeto.contabix.data.dto.SolicitacoesDTO;
import com.projeto.contabix.data.entity.SolicitacoesEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.service.SolicitacoesService;

@RestController
@RequestMapping("/solicitation")
public class SolicitacoesController {
    @Autowired
    private SolicitacoesService solicitacoesService;

    @PostMapping("/postNewSolicitation")
    public SolicitacoesDTO postNewSolicitation(@RequestBody SolicitacoesEntity solicitation) {
        return solicitacoesService.createSolicitation(solicitation);
    }

    @GetMapping("/getSolicitationsByUser")
    public List<SolicitacoesDTO> getSolicitationsByUser(@RequestBody UsuariosEntity cliente) {
        return solicitacoesService.getSolicitationsByUser(cliente);
    }
}