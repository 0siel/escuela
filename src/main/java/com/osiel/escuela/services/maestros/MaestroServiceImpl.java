package com.osiel.escuela.services.maestros;

import com.osiel.escuela.dtos.maestros.MaestroRequest;
import com.osiel.escuela.dtos.maestros.MaestroResponse;
import com.osiel.escuela.entities.Maestro;
import com.osiel.escuela.exceptions.EntidadRelacionadaException;
import com.osiel.escuela.mappers.MaestroMapper;
import com.osiel.escuela.repositories.MaestroRepository;
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
public class MaestroServiceImpl implements MaestroService{
    private final MaestroRepository maestroRepository;
    private final MaestroMapper maestroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {
        return maestroRepository.findAll().stream().map(maestroMapper::entityToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entityToResponse(getMaestroOrThrow(id));
    }



    @Override
    public MaestroResponse registrar(MaestroRequest request) {
        existsEmail(request.email());
        existsTelefono(request.telefono());
        Maestro maestro = maestroRepository.save(maestroMapper.requestToEntity(request));

        return maestroMapper.entityToResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {

        Maestro maestro = getMaestroOrThrow(id);

        boolean emailCambio = !maestro.getEmail().equalsIgnoreCase(request.email());
        boolean telefonoCambio = !maestro.getTelefono().equals(request.telefono());

        if(emailCambio){
            existsEmail(request.email());
            maestro.setEmail(request.email());
        }

        if(telefonoCambio){
            existsTelefono(request.telefono());
            maestro.setTelefono(request.telefono());
        }

        maestro.setNombre(request.nombre());
        maestro.setApellidoPaterno(request.apellidoPaterno());
        maestro.setApellidoMaterno(request.apellidoPaterno());

        Maestro actualizado = maestroRepository.save(maestro);
        log.info("Maestro actalizado: {}", actualizado);

        return maestroMapper.entityToResponse(actualizado);

    }


    @Override
    public void eliminar(Long id) {
        Maestro maestro = getMaestroOrThrow(id);

        if(maestro.getGrupos().isEmpty()){
            maestroRepository.delete(maestro);
            log.info("Maestro eliminado" + maestro.getId());
        }else{
            throw new EntidadRelacionadaException("No sepuede eliminar el maestro por que tiene grupos asignados");
        }
    }

    Maestro getMaestroOrThrow(Long id){
        return maestroRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("No se encontró el maestro con el ID: " + id ));
    }

    private void existsEmail(String email){
        if(maestroRepository.existsByEmailIgnoreCase(email)){
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + email.toLowerCase());
        }
    }

    private void existsTelefono(String telefono){
        if(maestroRepository.existsByTelefono(telefono)){
            throw new IllegalArgumentException("Ya existe un maestro registrado con ese teléfono: " + telefono);
        }
    }


}
