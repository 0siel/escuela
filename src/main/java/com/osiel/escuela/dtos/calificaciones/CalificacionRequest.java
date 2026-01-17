package com.osiel.escuela.dtos.calificaciones;

import java.math.BigDecimal;

public record CalificacionRequest(
        Long inscripcion,
        BigDecimal calificacion
) {
}
