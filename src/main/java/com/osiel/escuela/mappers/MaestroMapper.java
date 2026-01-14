package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.maestros.MaestroRequest;
import com.osiel.escuela.dtos.maestros.MaestroResponse;
import com.osiel.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

@Component
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro> {
    @Override
    public MaestroResponse entityToResponse(Maestro entity) {
        if(entity == null) return  null;

        return new MaestroResponse(
                entity.getId(),
                String.join(" ",
                        entity.getApellidoPatterno(), entity.getApellidoMaterno()),
                entity.getEmail(),
                entity.getTelefono()
        );
    }

    @Override
    public Maestro requestToEntity(MaestroRequest request) {
        if (request == null) return null;

        Maestro maestro = new Maestro();

        maestro.setNombre(request.nombre());
        maestro.setApellidoPatterno(request.apellidoPatterno());
        maestro.setApellidoMaterno(request.apellidoMaterno());
        maestro.setEmail(request.email());
        maestro.setTelefono(request.telefono());

        return maestro;
    }
}
