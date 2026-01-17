package com.osiel.escuela.services.grupos;

import com.osiel.escuela.dtos.grupos.GrupoRequest;
import com.osiel.escuela.dtos.grupos.GrupoResponse;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.mappers.GrupoMapper;
import com.osiel.escuela.repositories.AulaRepository;
import com.osiel.escuela.repositories.GrupoRepository;
import com.osiel.escuela.repositories.MaestroRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class GrupoServiceImplementation implements GrupoService{
    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final MaestroRepository maestroRepository;
    private final AulaRepository  aulaRepository;

    @Override
    public List<GrupoResponse> listar() {
        return grupoRepository.findAll().stream().map(grupoMapper::entityToResponse).toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entityToResponse(getGrupoOrThrow(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        return null;
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    private Grupo getGrupoOrThrow(Long id){
        return grupoRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No existe el grupo con el ID: " + id));
    }
}
