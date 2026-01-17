package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.inscripciones.InscripcionRequest;
import com.osiel.escuela.dtos.inscripciones.InscripcionResponse;
import com.osiel.escuela.entities.Alumno;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Inscripcion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion>{
    @Override
    public InscripcionResponse entityToResponse(Inscripcion entity) {
        if(entity == null) return null;

        BigDecimal calificacion = (entity.getCalificacion() != null)
                ? entity.getCalificacion().getCalificacion()
                : BigDecimal.ZERO;

        assert entity.getCalificacion() != null;
        return new InscripcionResponse(
                entity.getId(),
                entity.getAlumno().getId(),
                entity.getGrupo().getId(),
                calificacion
        );
    }

    @Override
    public Inscripcion requestToEntity(InscripcionRequest request) {
        return null;
    }

    public Inscripcion requestToEntity(Grupo grupo, Alumno alumno) {
        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setAlumno(alumno);
        inscripcion.setGrupo(grupo);
        inscripcion.setFechaInscripcion(LocalDate.now());
        return inscripcion;
    }

}
