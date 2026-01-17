package com.osiel.escuela.dtos.aulas;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record AulaRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size( min=5, max = 30, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,


        @NotNull(message = "La capacidad ds requerida")
        @Min(value = 10, message = "La capacidad mínima es 10")
        @Max(value = 50, message = "La capacidad máxima es 50")
        Integer capacidad
) {
}
