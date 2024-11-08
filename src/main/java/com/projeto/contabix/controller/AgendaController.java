package com.projeto.contabix.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.contabix.data.dto.AgendaDTO;
import com.projeto.contabix.data.dto.UsuariosDTO;
import com.projeto.contabix.service.AgendaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/calendar")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/getEventsByMonth")
    @ApiOperation(value = "Busca os eventos pelo mês, ano e usuário")
    public List<AgendaDTO> getEventsByMonth(@RequestParam String dateMonthYearFormatted, @RequestParam Long idUsuario) {
        return agendaService.getEventsByMonthAndYearAndUsuario(dateMonthYearFormatted, idUsuario);
    }

    @GetMapping("/getTodayEventsByUser")
    @ApiOperation(value = "Busca os eventos do dia atual pelo usuário")
    public List<AgendaDTO> getEventsByActualDayAndUsuario(@RequestParam Long idUsuario) {
        return agendaService.getEventsByActualDayAndUsuario(idUsuario);
    }

    @PostMapping("/getNotifications")
    @ApiOperation(value = "Busca as notificações para determinado usuário")
    public List<AgendaDTO> getNotification(@RequestBody UsuariosDTO usuariosDTO) {
        return agendaService.getEventsNotifications(usuariosDTO);
    }
    
}
