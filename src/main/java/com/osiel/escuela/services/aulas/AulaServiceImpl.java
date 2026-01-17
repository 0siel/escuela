package com.osiel.escuela.services.aulas;

import com.osiel.escuela.dtos.aulas.AulaRequest;
import com.osiel.escuela.dtos.aulas.AulaResponse;
import com.osiel.escuela.entities.Aula;
import com.osiel.escuela.mappers.AulaMapper;
import com.osiel.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService{
    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;


    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        return aulaRepository.findAll().stream().map(aulaMapper::entityToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entityToResponse(getAulaOrThrow(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        existsByName(request.nombre(), null);
        Aula aula = aulaRepository.save(aulaMapper.requestToEntity(request));

        return aulaMapper.entityToResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula = getAulaOrThrow(id);
        existsByName(request.nombre(), id);

        aula.setNombre(request.nombre());
        aula.setCapacidad(request.capacidad());

        Aula aulaUpdated = aulaRepository.save(aula);

        return aulaMapper.entityToResponse(aulaUpdated);

    }

    @Override
    public void eliminar(Long id) {
        aulaRepository.delete(getAulaOrThrow(id));

    }

    private Aula getAulaOrThrow(Long id){
        return aulaRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No existe el aula con el ID: " + id));

    }

    private void existsByName(String nombre, Long id){
        if(aulaRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)){
            throw new IllegalArgumentException("Ya existe un aula con ese nombre: " + nombre);
        }
    }
}
