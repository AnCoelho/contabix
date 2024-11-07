package com.projeto.contabix.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.contabix.data.dto.AgendaDTO;
import com.projeto.contabix.service.AgendaService;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/calendar")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/getEventsByMonth")
    @ApiOperation(value = "Busca os eventos pelo mês e ano")
    public List<AgendaDTO> getEventsByMonth(@RequestParam String dateMonthYearFormatted) {
        return agendaService.getEventsByMonthAndYear(dateMonthYearFormatted);
    }
}
