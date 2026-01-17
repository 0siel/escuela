package com.osiel.escuela.dtos.horarios;

import com.osiel.escuela.enums.DiaSemana;

public record HorarioResponse(
        Long id,
        String aula,
        String dia,
        String horaInicio,
        String horaFin,

        Long idGrupo,
        String curso
) {
}
