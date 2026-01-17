package com.osiel.escuela.services.calificaciones;

import com.osiel.escuela.dtos.calificaciones.CalificacionRequest;
import com.osiel.escuela.dtos.calificaciones.CalificacionResponse;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.mappers.CalificacionMapper;
import com.osiel.escuela.repositories.CalificacionRepository;
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
public class CalificacionServiceImpl implements CalificacionService{
    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;

    @Override
    public List<CalificacionResponse> listar() {
        return calificacionRepository.findAll().stream().map(calificacionMapper::entityToResponse).toList();
    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entityToResponse(getCalificacionOrThrow(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
        return null;
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {
        calificacionRepository.delete(getCalificacionOrThrow(id));

    }

    private Calificacion getCalificacionOrThrow(Long id){
        return calificacionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontrón ninguna calificación con ese ID: " + id));
    }
}
