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

    @PostMapping("/getSolicitationsByUser")
    public List<SolicitacoesDTO> getSolicitationsByUser(@RequestBody UsuariosEntity contador) {
        return solicitacoesService.getSolicitationsByUser(contador);
    }
}