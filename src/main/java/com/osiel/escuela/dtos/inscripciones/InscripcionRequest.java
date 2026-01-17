package com.osiel.escuela.dtos.inscripciones;

import com.osiel.escuela.entities.Alumno;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.entities.Grupo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record InscripcionRequest(

        @NotNull(message = "El alumno es requerido")
        Long alumno,

        @NotNull(message = "El grupo es requerido")
        Long grupo,

        @OneToOne(mappedBy = "inscripcion")
        Long calificacion
) {
}
