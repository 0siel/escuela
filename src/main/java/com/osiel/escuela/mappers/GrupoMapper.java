package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.grupos.GrupoRequest;
import com.osiel.escuela.dtos.grupos.GrupoResponse;
import com.osiel.escuela.entities.Curso;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.repositories.AulaRepository;
import com.osiel.escuela.repositories.CursoRepository;
import com.osiel.escuela.repositories.MaestroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo> {
    private final CursoRepository cursoRepository;
    private final AulaRepository aulaRepository;
    private final MaestroRepository maestroRepository;
    @Override
    public GrupoResponse entityToResponse(Grupo entity) {
        if(entity == null) {return null;}



        return new GrupoResponse(
                entity.getCurso().getNombre(),
                String.join(" ", entity.getMaestro().getNombre(), entity.getMaestro().getApellidoPaterno(), entity.getMaestro().getApellidoMaterno()),
                entity.getAula().getNombre(),
                entity.getHorarios().stream().map(horario ->String.join(" ", horario.getDia().getDescripcion(), horario.getHoraInicio().toString(), horario.getHoraFin().toString())).toList(),
                entity.getPeriodo()

        );
    }

    @Override
    public Grupo requestToEntity(GrupoRequest request) {
        if(request == null){return null;}

        Grupo grupo = new Grupo();
        grupo.setPeriodo(request.periodo());

        grupo.setAula(aulaRepository.getReferenceById(request.aulaId()));
        grupo.setCurso(cursoRepository.getReferenceById(request.cursoId()));
        grupo.setMaestro(maestroRepository.getReferenceById(request.maestroId()));

        return grupo;
    }
}
