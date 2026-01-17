package com.osiel.escuela.dtos.cursos;

import jakarta.validation.constraints.*;

public record CursoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 100, message = "El nombre dbe tener entre 5 y 100 caracteres")
        String nombre,
        @NotBlank(message = "La descripción es requerida")
        @Size(min = 5, max = 200, message = "La descripción debe tener entre 5 y 200 caracteres")
        String descripcion,

        @NotNull(message = "Los créditos son requeridos")
        @Min(value = 1, message = "El valor minimo para los créditos es 1")
        @Max(value = 10, message = "El valor máximo para los créditos es 10")
        Integer creditos
) {
}
