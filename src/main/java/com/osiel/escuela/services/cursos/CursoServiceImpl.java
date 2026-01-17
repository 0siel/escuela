package com.osiel.escuela.services.cursos;

import com.osiel.escuela.dtos.cursos.CursoRequest;
import com.osiel.escuela.dtos.cursos.CursoResponse;
import com.osiel.escuela.entities.Curso;
import com.osiel.escuela.mappers.CursoMapper;
import com.osiel.escuela.repositories.CursoRepository;
import com.osiel.escuela.repositories.GrupoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CursoServiceImpl implements CursoService{
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final GrupoRepository grupoRepository;

    @Override
    public List<CursoResponse> listar() {
        return cursoRepository.findAll().stream().map(cursoMapper::entityToResponse).toList();
    }

    @Override
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entityToResponse(getCursoOrThrow(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        existsNombre(request.nombre(), null);
        return cursoMapper.entityToResponse(cursoRepository.save(cursoMapper.requestToEntity(request)));

    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        existsNombre(request.nombre(), id);

        Curso curso = getCursoOrThrow(id);
        curso.setNombre(request.nombre());
        curso.setDescripcion(request.descripcion());
        curso.setCreditos(request.creditos());

        Curso cursoUpdated = cursoRepository.save(curso);

        return cursoMapper.entityToResponse(cursoUpdated);
    }

    @Override
    public void eliminar(Long id) {
        getCursoOrThrow(id);

        if (grupoRepository.existsByCursoId(id)) {
            throw new IllegalArgumentException("No se puede eliminar: El curso tiene grupos activos. " +
                    "Elimina primero los grupos relacionados.");
        }
        cursoRepository.delete(getCursoOrThrow(id));

    }

    private Curso getCursoOrThrow(Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El curso con el id " + id + " no existe"));
    }

    private void existsNombre(String nombre, Long id){
        if(cursoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)){
            throw new IllegalArgumentException("Ya existe un curso con ese nombre: " + nombre);
        }
    }
}
