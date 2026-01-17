package com.osiel.escuela.dtos.inscripciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        Long idAlumno,
        Long idGrupo,
        @Null
        BigDecimal calificacion
) {
}
