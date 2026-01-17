package com.osiel.escuela.dtos.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GrupoRequest(
        @NotNull(message = "El ID del curso es requerido")
        Long cursoId,
        @NotNull(message = "El Id del maestro es requerido")
        Long maestroId,
        @NotNull(message = "El ID del aula es requerido")
        Long aulaId,
        @NotBlank(message = "El periodo del curso es requerido ej. 2026-1")
        @Size(min = 6, max = 20, message = "El periodo debe tener entre 6 y 20 caracteres")
        String periodo
) {
}
