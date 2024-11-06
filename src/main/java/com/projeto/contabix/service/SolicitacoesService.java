package com.projeto.contabix.service;

import com.projeto.contabix.data.dto.SolicitacoesDTO;
import com.projeto.contabix.data.entity.SolicitacoesEntity;
import com.projeto.contabix.data.entity.UsuariosEntity;
import com.projeto.contabix.repository.SolicitacoesRepository;
import com.projeto.contabix.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitacoesService {

    @Autowired
    private SolicitacoesRepository solicitacoesRepository;

    public SolicitacoesDTO createSolicitacao(SolicitacoesDTO solicitacoesDTO) {
        SolicitacoesEntity solicitacoesEntity = ModelMapperUtils.map(solicitacoesDTO, new SolicitacoesEntity());
        solicitacoesEntity = solicitacoesRepository.save(solicitacoesEntity);
        return ModelMapperUtils.map(solicitacoesEntity, new SolicitacoesDTO());
    }

    public List<SolicitacoesDTO> getSolicitacoesByCliente(UsuariosEntity idCliente) {
        List<SolicitacoesEntity> solicitacoesEntities = solicitacoesRepository.findByIdCliente(idCliente);
        return solicitacoesEntities.stream()
                .map(entity -> ModelMapperUtils.map(entity, new SolicitacoesDTO()))
                .collect(Collectors.toList());
    }

    public List<SolicitacoesDTO> getSolicitacoesByContador(UsuariosEntity idContador) {
        List<SolicitacoesEntity> solicitacoesEntities = solicitacoesRepository.findByIdContador(idContador);
        return solicitacoesEntities.stream()
                .map(entity -> ModelMapperUtils.map(entity, new SolicitacoesDTO()))
                .collect(Collectors.toList());
    }

    public List<SolicitacoesDTO> getAllSolicitacoesOrderedByStatus() {
        List<SolicitacoesEntity> solicitacoesEntities = solicitacoesRepository.findAllByOrderByStatusAsc();
        return solicitacoesEntities.stream()
                .map(entity -> ModelMapperUtils.map(entity, new SolicitacoesDTO()))
                .collect(Collectors.toList());
    }
}