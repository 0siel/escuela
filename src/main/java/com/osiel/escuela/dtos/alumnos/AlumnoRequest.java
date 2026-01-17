package com.osiel.escuela.dtos.alumnos;

import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Inscripcion;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record AlumnoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 3, max = 50, message = "El apellido materno debe tener entre 3 y 50 caracteres")
        String apellidoMaterno

) {

}
