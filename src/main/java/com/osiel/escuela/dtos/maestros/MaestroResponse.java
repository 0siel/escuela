package com.osiel.escuela.dtos.maestros;

import java.util.List;

public record MaestroResponse(Long id,
                              String nombre,
                              String email,
                              String telefono,
                              List<DatosCurso> cursos) {
}