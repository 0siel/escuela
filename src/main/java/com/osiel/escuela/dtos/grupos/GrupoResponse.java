package com.osiel.escuela.dtos.grupos;

import java.util.List;

public record GrupoResponse(
        String nombreCurso,
        String nombreMaestro,
        String nombreAula,
        List<String> horarios,
        String periodo
) {
}
