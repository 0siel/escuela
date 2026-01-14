package com.osiel.escuela.dtos.maestros;

import java.util.List;

public record DatosCurso(
        String nombre,
        String aula,
        List<String> horarios,
        String periodo
) {
}
