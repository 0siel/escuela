package com.osiel.escuela.dtos.aulas;

public record AulaResponse(
        Long id,
        String nombre,
        Integer capacidad
) {
}
