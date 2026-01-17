package com.osiel.escuela.services.inscripcion;

import com.osiel.escuela.dtos.inscripciones.InscripcionRequest;
import com.osiel.escuela.dtos.inscripciones.InscripcionResponse;
import com.osiel.escuela.entities.Alumno;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Inscripcion;
import com.osiel.escuela.mappers.InscripcionMapper;
import com.osiel.escuela.repositories.AlumnoRepository;
import com.osiel.escuela.repositories.CalificacionRepository;
import com.osiel.escuela.repositories.GrupoRepository;
import com.osiel.escuela.repositories.InscripcionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class InscripcionServiceImpl implements InscripcionService{

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper inscripcionMapper;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final CalificacionRepository calificacionRepository;


    @Override
    public List<InscripcionResponse> listar() {
        return inscripcionRepository.findAll().stream().map(inscripcionMapper::entityToResponse).toList();
    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entityToResponse(getInscripcionOrThrow(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        log.info("Inscripcion ini");

        Grupo grupo = grupoRepository.findById(request.grupo()).orElseThrow(()-> new NoSuchElementException("No existe un grupo con este id: " + request.grupo()));
        Alumno alumno = alumnoRepository.findById(request.alumno()).orElseThrow(()-> new NoSuchElementException("No existe un alumno con este id: " + request.alumno()));

        if(inscripcionRepository.existsByAlumnoIdAndGrupoId(alumno.getId(), grupo.getId())){
            throw new IllegalArgumentException(String.format("El alumno %s ya está inscrito en este grupo: %s", alumno.getId(), grupo.getId()));
        }

        log.info("Inscripcion validada");

        Inscripcion inscripcion = inscripcionRepository.save(inscripcionMapper.requestToEntity(grupo, alumno));


        log.info("Inscripcion guardada");

        return inscripcionMapper.entityToResponse(inscripcion);

    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        Grupo grupo = grupoRepository.findById(request.grupo()).orElseThrow(()-> new NoSuchElementException("No existe un grupo con este id: " + request.grupo()));
        Alumno alumno = alumnoRepository.findById(request.alumno()).orElseThrow(()-> new NoSuchElementException("No existe un alumno con este id: " + request.alumno()));


        if(inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(alumno.getId(), grupo.getId(), id)){
            throw new IllegalArgumentException(String.format("El alumno %s ya s está inscrito en este grupo: %s", alumno.getId(), grupo.getId()));
        }


        Inscripcion inscripcion = getInscripcionOrThrow(id);

        if(Objects.equals(grupo, inscripcion.getGrupo()) && Objects.equals(alumno, inscripcion.getAlumno())){
            throw new IllegalArgumentException(String.format("Ya se realizó una inscripción con el alumno %s y el grupo %s", alumno.getId(), grupo.getId()));
        };
        inscripcion.setAlumno(alumno);
        inscripcion.setGrupo(grupo);



        return inscripcionMapper.entityToResponse(
                inscripcionRepository.save(inscripcion));
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = getInscripcionOrThrow(id);
        if (inscripcion.getCalificacion() != null && inscripcion.getCalificacion().getCalificacion() != null) {
            throw new RuntimeException("No es posible eliminar la inscripción: El alumno ya cuenta con una calificación registrada.");
        }
        inscripcionRepository.delete(getInscripcionOrThrow(id));

    }

    private Inscripcion getInscripcionOrThrow(Long id){
        return inscripcionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No existe un horario con ese id: "+ id));
    }
}
