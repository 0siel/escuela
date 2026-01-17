package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.calificaciones.CalificacionRequest;
import com.osiel.escuela.dtos.calificaciones.CalificacionResponse;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.entities.Inscripcion;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion> {
    @Override
    public CalificacionResponse entityToResponse(Calificacion entity) {
        if(entity == null) return null;
        return new CalificacionResponse(
                entity.getId(),
                entity.getInscripcion().getId(),
                entity.getCalificacion(),
                entity.getFechaRegistro()
        );
    }

    @Override
    public Calificacion requestToEntity(CalificacionRequest request) {
        if(request == null) return null;

        Calificacion calificacion = new Calificacion();
        calificacion.setCalificacion(request.calificacion());

        return calificacion;
    }

    public Calificacion requestToEntity(CalificacionRequest request, Inscripcion inscripcion) {
        if(request == null) return null;

        Calificacion calificacion = requestToEntity(request);

        calificacion.setInscripcion(inscripcion);
        calificacion.setFechaRegistro(LocalDate.now());

        return calificacion;
    }
}
