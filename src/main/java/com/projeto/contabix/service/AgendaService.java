package com.projeto.contabix.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.contabix.data.dto.AgendaDTO;
import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.data.entity.AgendaEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.exception.BusinessException;
import com.projeto.contabix.exception.NotFoundException;
import com.projeto.contabix.repository.AgendaRepository;
import com.projeto.contabix.repository.UsuariosRepository;
import com.projeto.contabix.utils.ModelMapperUtils;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<AgendaDTO> getEventsByMonthAndYearAndUsuario(String mesAno, Long idUsuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse("01/" + mesAno, formatter);
        Long mes = (long) data.getMonthValue();
        Long ano = (long) data.getYear();

        UsuariosEntity usuario = usuariosRepository.findById(idUsuario).get();

        List<AgendaDTO> agendaDTOs = ModelMapperUtils.mapList(
                agendaRepository.findAllByMonthAndYearAndUsuario(mes, ano, usuario),
                AgendaDTO.class);

        if (agendaDTOs.isEmpty() || agendaDTOs == null)
            throw new NotFoundException("404", "Não há eventos para esse mês");

        return agendaDTOs;
    }

    @Transactional
    public List<AgendaDTO> getEventsNotifications(UsuariosDTO usuariosDTO) {
        if (usuariosDTO.getIdUsuario() == null) {
            throw new BusinessException("ID_USUARIO_NULO", "O ID do usuário é necessário para buscar notificações");
        }

        UsuariosEntity usuario = usuariosRepository.findById(usuariosDTO.getIdUsuario())
                .orElseThrow(() -> new BusinessException("USUARIO_NAO_ENCONTRADO", "Usuário não encontrado"));

        List<AgendaEntity> notifications = agendaRepository.findAllByNotificadoAndDestinatario(false, usuario);
        if (notifications.isEmpty()) {
            throw new BusinessException("SEM_NOTIFICACOES", "Você não possui nenhuma notificação no momento");
        }

        for (AgendaEntity notification : notifications) {
            notification.setNotificado(true);
            agendaRepository.save(notification);
        }

        return ModelMapperUtils.mapList(notifications, AgendaDTO.class);
    }

    public List<AgendaDTO> getEventsByActualDayAndUsuario(Long idUsuario) {
        LocalDate today = LocalDate.now();
        UsuariosEntity usuario = usuariosRepository.findById(idUsuario).get();

        List<AgendaDTO> agendaDTOs = ModelMapperUtils.mapList(agendaRepository.findAllByDateAndUsuario(today, usuario),
                AgendaDTO.class);

        if (agendaDTOs.isEmpty() || agendaDTOs == null)
            throw new NotFoundException("404", "Não há eventos para hoje");

        return agendaDTOs;
    }
}
