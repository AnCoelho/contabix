package com.projeto.contabix.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projeto.contabix.data.dto.SolicitacoesDTO;
import com.projeto.contabix.data.entity.AgendaEntity;
import com.projeto.contabix.data.entity.SolicitacoesEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.repository.AgendaRepository;
import com.projeto.contabix.repository.SolicitacoesRepository;
import com.projeto.contabix.utils.ModelMapperUtils;

import jakarta.transaction.Transactional;

@Service
public class SolicitacoesService {
    @Autowired
    private SolicitacoesRepository solicitacoesRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Transactional
    public SolicitacoesDTO createSolicitation(SolicitacoesEntity solicitation) {
        SolicitacoesEntity solicitacoesEntity = solicitation;
        solicitacoesEntity.setDataAbertura(LocalDateTime.now());
        solicitacoesEntity.setStatus("Em Aberto");

        validateSolicitation(solicitacoesEntity);

        SolicitacoesEntity savedSolicitation = solicitacoesRepository.save(solicitacoesEntity);

        createAgendaEvent(savedSolicitation);

        return ModelMapperUtils.map(savedSolicitation, new SolicitacoesDTO());
    }

    public List<SolicitacoesDTO> getSolicitationsByUser(UsuariosEntity cliente) {
        if (cliente == null || cliente.getIdUsuario() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inválido.");
        }

        return ModelMapperUtils.mapList(solicitacoesRepository.findByCliente(cliente), SolicitacoesDTO.class);
    }

    private void validateSolicitation(SolicitacoesEntity solicitation) {
        if (solicitation.getCliente() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente é obrigatório.");
        }
        if (solicitation.getServico() == null || solicitation.getServico().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Serviço é obrigatório.");
        }
        if (solicitation.getAssunto() == null || solicitation.getAssunto().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assunto é obrigatório.");
        }
        if (solicitation.getDescricao() == null || solicitation.getDescricao().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Descrição é obrigatória.");
        }
        if (solicitation.getPrazo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prazo é obrigatório.");
        }
    }

    private void createAgendaEvent(SolicitacoesEntity solicitation) {
        AgendaEntity agendaEvent = new AgendaEntity();
        agendaEvent.setUsuario(solicitation.getCliente());
        agendaEvent.setSolicitacao(solicitation);
        agendaEvent.setSolicitante(solicitation.getCliente());
        agendaEvent.setDestinatario(solicitation.getContador());
        agendaEvent.setDescricao("Novo evento criado para a solicitação " + solicitation.getIdSolicitacao());

        LocalDateTime eventDateTime = solicitation.getPrazo();
        Date eventDate = Date.valueOf(eventDateTime.toLocalDate());
        agendaEvent.setDataEvento(eventDate);

        agendaEvent.setTipoEvento("Solicitação");
        agendaEvent.setNotificado(false);

        agendaRepository.save(agendaEvent);
    }
}