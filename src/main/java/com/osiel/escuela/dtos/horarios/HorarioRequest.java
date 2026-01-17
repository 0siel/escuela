package com.osiel.escuela.dtos.horarios;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;

public record HorarioRequest(

        @NotNull(message = "El día de la semana es requrido")
        @Enumerated(EnumType.STRING)
        DiaSemana dia,

        @NotNull(message = "La hora de inicio es requrida")
        //@Pattern(regexp = "^[0-9]{2}:[0-9]{2}$", message = "El formato de la hora de inicio es HH:mm")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaInicio,

        @NotNull(message = "La hora de inicio es requerida")
        //@Pattern(regexp = "^[0-9]{2}:[0-9]{2}$", message = "El formato de la hora de inicio es HH:mm")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaFin,

        @NotNull(message = "El ID del grupo es requerido")
        Long idGrupo
) {
}
