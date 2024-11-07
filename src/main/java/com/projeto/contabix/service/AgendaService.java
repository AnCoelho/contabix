package com.projeto.contabix.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.contabix.data.dto.AgendaDTO;
import com.projeto.contabix.exception.NotFoundException;
import com.projeto.contabix.repository.AgendaRepository;
import com.projeto.contabix.utils.ModelMapperUtils;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    public List<AgendaDTO> getEventsByMonthAndYear(String mesAno) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse("01/" + mesAno, formatter);
        Long mes = (long) data.getMonthValue();
        Long ano = (long) data.getYear();

        List<AgendaDTO> agendaEntities = ModelMapperUtils.mapList(agendaRepository.findAllByMonthAndYear(mes, ano),
                AgendaDTO.class);

        if (agendaEntities.isEmpty() || agendaEntities == null)
            throw new NotFoundException("404", "Não há eventos para esse mês");

        return agendaEntities;
    }
}
