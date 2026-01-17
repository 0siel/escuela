package com.osiel.escuela.services.grupos;

import com.osiel.escuela.dtos.grupos.GrupoRequest;
import com.osiel.escuela.dtos.grupos.GrupoResponse;
import com.osiel.escuela.entities.Aula;
import com.osiel.escuela.entities.Curso;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Maestro;
import com.osiel.escuela.mappers.GrupoMapper;
import com.osiel.escuela.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class GrupoServiceImplementation implements GrupoService{
    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final HorarioRepository horarioRepository;
    private final InscripcionRepository inscripcionRepository;
    private final CursoRepository cursoRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;



    @Override
    public List<GrupoResponse> listar() {
        return grupoRepository.findAll().stream().map(grupoMapper::entityToResponse).toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entityToResponse(getGrupoOrThrow(id));
    }

    @Transactional
    public GrupoResponse registrar(GrupoRequest request) {

        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new NoSuchElementException("Curso no encontrado"));

        Maestro maestro = maestroRepository.findById(request.maestroId())
                .orElseThrow(() -> new NoSuchElementException("Maestro no encontrado"));

        Aula aula = aulaRepository.findById(request.aulaId())
                .orElseThrow(() -> new NoSuchElementException("Aula no encontrada"));


        boolean existe = grupoRepository.existsByCursoAndMaestroAndAulaAndPeriodo(
                curso, maestro, aula, request.periodo()
        );

        if (existe) {
            throw new IllegalArgumentException("Conflicto: Ya existe un grupo registrado con el mismo " +
                    "curso, maestro y aula para el periodo " + request.periodo());
        }


        Grupo grupo = grupoMapper.requestToEntity(request);


        grupo.setCurso(curso);
        grupo.setMaestro(maestro);
        grupo.setAula(aula);

        return grupoMapper.entityToResponse(grupoRepository.save(grupo));
    }

    @Transactional
    public GrupoResponse actualizar(GrupoRequest request, Long id ) {

        Grupo grupoExistente = grupoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Grupo no encontrado con ID: " + id));


        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new NoSuchElementException("Curso no encontrado"));

        Maestro maestro = maestroRepository.findById(request.maestroId())
                .orElseThrow(() -> new NoSuchElementException("Maestro no encontrado"));

        Aula aula = aulaRepository.findById(request.aulaId())
                .orElseThrow(() -> new NoSuchElementException("Aula no encontrada"));


        boolean existeOtroIgual = grupoRepository.existsByCursoAndMaestroAndAulaAndPeriodoAndIdNot(
                curso, maestro, aula, request.periodo(), id
        );

        if (existeOtroIgual) {
            throw new IllegalArgumentException("No se puede actualizar: Ya existe otro grupo con esta misma combinación.");
        }

        grupoExistente.setPeriodo(request.periodo());
        grupoExistente.setCurso(curso);
        grupoExistente.setMaestro(maestro);
        grupoExistente.setAula(aula);


        return grupoMapper.entityToResponse(grupoRepository.save(grupoExistente));
    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = getGrupoOrThrow(id);
        if (horarioRepository.existsByGrupoId(id)) {
            throw new RuntimeException("No se puede eliminar: El grupo tiene horarios registrados. " +
                    "Elimina primero los horarios relacionados.");
        }

        if (inscripcionRepository.existsByGrupoId(id)) {
            throw new RuntimeException("No se puede eliminar: El grupo tiene inscripciones registradas. " +
                    "Elimina primero las inscripciones relacionadas.");
        }

        grupoRepository.delete(getGrupoOrThrow(id));

    }

    private Grupo getGrupoOrThrow(Long id){
        return grupoRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No existe el grupo con el ID: " + id));
    }
}
