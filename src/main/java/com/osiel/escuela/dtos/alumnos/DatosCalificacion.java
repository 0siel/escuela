package com.osiel.escuela.dtos.alumnos;

import java.math.BigDecimal;

public record DatosCalificacion(
        String nombreCurso,
        String periodo,
        BigDecimal calificacion
) {
}
