package com.osiel.escuela.dtos.calificaciones;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalificacionResponse(
        Long id,
        Long inscripcion,
        BigDecimal calificacion,
        LocalDate fechaRegistro
) {
}
