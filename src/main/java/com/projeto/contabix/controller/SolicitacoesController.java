package com.projeto.contabix.controller;

import com.projeto.contabix.data.dto.SolicitacoesDTO;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.service.SolicitacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacoesController {

    @Autowired
    private SolicitacoesService solicitacoesService;

    @PostMapping
    public ResponseEntity<SolicitacoesDTO> createSolicitacao(@RequestBody SolicitacoesDTO solicitacoesDTO) {
        SolicitacoesDTO createdSolicitacao = solicitacoesService.createSolicitacao(solicitacoesDTO);
        return ResponseEntity.ok(createdSolicitacao);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<SolicitacoesDTO>> getSolicitacoesByCliente(@PathVariable UsuariosEntity idCliente) {
        List<SolicitacoesDTO> solicitacoes = solicitacoesService.getSolicitacoesByCliente(idCliente);
        return ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/contador/{idContador}")
    public ResponseEntity<List<SolicitacoesDTO>> getSolicitacoesByContador(@PathVariable UsuariosEntity idContador) {
        List<SolicitacoesDTO> solicitacoes = solicitacoesService.getSolicitacoesByContador(idContador);
        return ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/ordered-by-status")
    public ResponseEntity<List<SolicitacoesDTO>> getAllSolicitacoesOrderedByStatus() {
        List<SolicitacoesDTO> solicitacoes = solicitacoesService.getAllSolicitacoesOrderedByStatus();
        return ResponseEntity.ok(solicitacoes);
    }
}