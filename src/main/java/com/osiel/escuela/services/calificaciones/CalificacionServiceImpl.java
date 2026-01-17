package com.osiel.escuela.services.calificaciones;

import com.osiel.escuela.dtos.calificaciones.CalificacionRequest;
import com.osiel.escuela.dtos.calificaciones.CalificacionResponse;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.entities.Inscripcion;
import com.osiel.escuela.mappers.CalificacionMapper;
import com.osiel.escuela.repositories.CalificacionRepository;
import com.osiel.escuela.repositories.InscripcionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CalificacionServiceImpl implements CalificacionService{
    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;
    private final InscripcionRepository inscripcionRepository;

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
        Inscripcion inscripcion = inscripcionRepository.findById(request.inscripcion()).orElseThrow(() -> new NoSuchElementException("No se encontró ninguna inscripcion con este ID: " + request.inscripcion()));
        if(inscripcion.getCalificacion()!= null){
            throw new IllegalArgumentException("Ya se registró una calificacion para esta inscripcion");
        }

        Calificacion calificacionSaved = calificacionRepository.save(calificacionMapper.requestToEntity(request, inscripcion));

        return calificacionMapper.entityToResponse(calificacionSaved);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        Calificacion calificacionToUpdate = getCalificacionOrThrow(id);

        Inscripcion inscripcion = inscripcionRepository.findById(request.inscripcion()).orElseThrow(() -> new NoSuchElementException("No se encontró ninguna inscripcion con este ID: " + request.inscripcion()));
        if(inscripcion.getCalificacion()!= null && !Objects.equals(calificacionToUpdate.getInscripcion().getId(), inscripcion.getId())){
            throw new IllegalArgumentException("Ya se registró una calificacion para esta inscripcion");
        }

        calificacionToUpdate.setCalificacion(request.calificacion());
        calificacionToUpdate.setInscripcion(inscripcion);
        calificacionToUpdate.setFechaRegistro(LocalDate.now());

        return calificacionMapper.entityToResponse(calificacionRepository.save(calificacionToUpdate));

    }

    @Override
    public void eliminar(Long id) {
        calificacionRepository.delete(getCalificacionOrThrow(id));

    }

    private Calificacion getCalificacionOrThrow(Long id){
        return calificacionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontrón ninguna calificación con ese ID: " + id));
    }
}
