package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.horarios.HorarioRequest;
import com.osiel.escuela.dtos.horarios.HorarioResponse;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Horario;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario>{
    @Override
    public HorarioResponse entityToResponse(Horario entity) {
        if(entity == null) return null;

        return new HorarioResponse(
                entity.getId(),
                entity.getGrupo().getAula().getNombre(),
                entity.getDia().getDescripcion(),
                entity.getHoraInicio().toString(),
                entity.getHoraFin().toString(),
                entity.getGrupo().getId(),
                entity.getGrupo().getCurso().getNombre()
        );
    }

    @Override
    public Horario requestToEntity(HorarioRequest request) {
        if(request == null)return null;

        Horario horario = new Horario();

        horario.setDia(request.dia());
        horario.setHoraInicio(request.horaInicio());
        horario.setHoraFin(request.horaFin());


        return horario;
    }

    public Horario requestToEntity(HorarioRequest request, Grupo grupo) {
        if(request == null)return null;

        Horario horario = requestToEntity(request);

        horario.setGrupo(grupo);



        return horario;
    }
}
