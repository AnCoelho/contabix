package com.projeto.contabix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.contabix.data.dto.TipoUsuarioDTO;
import com.projeto.contabix.repository.TipoUsuarioRepository;
import com.projeto.contabix.utils.ModelMapperUtils;

@Service
public class TipoUsuarioService {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<TipoUsuarioDTO> getAllTiposUsuarios() {
        return ModelMapperUtils.mapList(tipoUsuarioRepository.findAll(), TipoUsuarioDTO.class);
    }
}
