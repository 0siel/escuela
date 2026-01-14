package com.osiel.escuela.mappers;


import com.osiel.escuela.dtos.maestros.DatosCurso;
import com.osiel.escuela.dtos.maestros.MaestroRequest;
import com.osiel.escuela.dtos.maestros.MaestroResponse;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro> {
    @Override
    public MaestroResponse entityToResponse(Maestro entity) {
        if(entity == null) return  null;

        List<DatosCurso> cursos = grupoToDatosCurso(entity);
        return new MaestroResponse(
                entity.getId(),
                String.join(" ",
                        entity.getApellidoPaterno(), entity.getApellidoMaterno()),
                entity.getEmail(),
                entity.getTelefono(),
                cursos
        );
    }

    @Override
    public Maestro requestToEntity(MaestroRequest request) {
        if (request == null) return null;

        Maestro maestro = new Maestro();

        maestro.setNombre(request.nombre());
        maestro.setApellidoPaterno(request.apellidoPaterno());
        maestro.setApellidoMaterno(request.apellidoMaterno());
        maestro.setEmail(request.email());
        maestro.setTelefono(request.telefono());

        return maestro;
    }

    private List< DatosCurso> grupoToDatosCurso(Maestro maestro){
        if(maestro == null) return null;

        return maestro.getGrupos().stream().map(grupo -> new DatosCurso(
                grupo.getCurso().getNombre(),
                grupo.getAula().getNombre(),
                grupo.getHorarios().stream().map(h -> String.join(" ", h.getDia().getDescripcion(), h.getHoraInicio(), h.getHoraFin())).toList(),
                grupo.getPeriodo()
        )).toList();

        /*
        List<DatosCurso> datosCurso = new ArrayList<>();

        maestro.getGrupos().forEach(grupo->{
            String nombre = grupo.getCurso().getNombre();
            String aula = grupo.getAula().getNombre();
            List<String> horarios = grupo.getHorarios().stream().map(horario -> {
                return String.join(" ",
                        horario.getDia().getDescripcion(),
                        horario.getHoraInicio(),
                        horario.getHoraFin()
                );
            }).toList();

            String periodo = grupo.getPeriodo();
            DatosCurso curso  = new DatosCurso(nombre, aula, horarios, periodo);
            datosCurso.add(curso);

        });
        return  datosCurso;
        * */
    }
}
