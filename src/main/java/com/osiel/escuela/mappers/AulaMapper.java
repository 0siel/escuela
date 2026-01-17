package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.aulas.AulaRequest;
import com.osiel.escuela.dtos.aulas.AulaResponse;
import com.osiel.escuela.entities.Aula;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper implements CommonMapper<AulaRequest, AulaResponse, Aula> {
    @Override
    public AulaResponse entityToResponse(Aula entity) {
        if(entity == null)return null;

        return new AulaResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getCapacidad()
        );

    }

    @Override
    public Aula requestToEntity(AulaRequest request) {
        if(request == null) return null;

        Aula aula = new Aula();

        aula.setNombre(request.nombre());
        aula.setCapacidad(request.capacidad());

        return aula;
    }
}
