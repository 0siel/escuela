package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.cursos.CursoRequest;
import com.osiel.escuela.dtos.cursos.CursoResponse;
import com.osiel.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso> {
    @Override
    public CursoResponse entityToResponse(Curso entity) {
        if(entity == null)return null;

        return new CursoResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getCreditos()
        );
    }

    @Override
    public Curso requestToEntity(CursoRequest request) {
        if(request == null)return null;
        Curso curso = new Curso();
        curso.setNombre(request.nombre());
        curso.setDescripcion(request.descripcion());
        curso.setCreditos(request.creditos());
        return curso;
    }
}
