package com.osiel.escuela.services.horarios;

import com.osiel.escuela.dtos.horarios.HorarioRequest;
import com.osiel.escuela.dtos.horarios.HorarioResponse;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Horario;
import com.osiel.escuela.mappers.HorarioMapper;
import com.osiel.escuela.repositories.GrupoRepository;
import com.osiel.escuela.repositories.HorarioRepository;
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
public class HorarioServiceImpl implements HorarioService{
    private final HorarioRepository horarioRepository;
    private final GrupoRepository grupoRepository;
    private final HorarioMapper horarioMapper;
    @Override
    public List<HorarioResponse> listar() {
        return horarioRepository.findAll().stream().map(horarioMapper::entityToResponse).toList();
    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entityToResponse(getHorarioOrThrow(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        Grupo grupo = grupoRepository.findById(request.idGrupo()).orElseThrow(()-> new NoSuchElementException("No existe el grupo con el ID: " + request.idGrupo()));

        validarHorario(request, grupo);
        Horario horario = horarioRepository.save(horarioMapper.requestToEntity(request, grupo));
        return horarioMapper.entityToResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        if(request == null) return null;
        Horario horarioToUpdate = getHorarioOrThrow(id);

        Grupo grupo = grupoRepository.findById(request.idGrupo()).orElseThrow(()-> new NoSuchElementException("No existe el grupo con el ID: " + id));

        validarHorario(request, grupo);

        horarioToUpdate.setGrupo(grupo);
        horarioToUpdate.setDia(request.dia());
        horarioToUpdate.setHoraFin(request.horaFin());
        horarioToUpdate.setHoraInicio(request.horaInicio());

        return horarioMapper.entityToResponse(horarioRepository.save(horarioToUpdate));

    }

    @Override
    public void eliminar(Long id) {
        horarioRepository.delete(getHorarioOrThrow(id));

    }

    private Horario getHorarioOrThrow(Long id){
        return horarioRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No existe ningún horario con ese ID: " + id));
    }

    private void validarHorario(HorarioRequest request, Grupo grupo){
        if(!request.horaInicio().isBefore(request.horaFin())){
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de término");
        }

        if(horarioRepository.existeConflictoMaestro(
                grupo.getMaestro().getId(),
                grupo.getPeriodo(),
                request.dia(),
                request.horaInicio(),
                request.horaFin()

        )){
            throw new IllegalArgumentException(
                    String.format("Conflicto: el maestro %s : %s ya tiene un horario registrado: %s %s-%s",
                            grupo.getMaestro().getId(),
                            String.join(" ", grupo.getMaestro().getNombre(),grupo.getMaestro().getApellidoPaterno(),grupo.getMaestro().getApellidoMaterno()),
                            request.dia(),
                            request.horaInicio(),
                            request.horaFin()));

        };

        if(horarioRepository.existeConflictoAula(
                grupo.getAula().getId(),
                grupo.getPeriodo(),
                request.dia(),
                request.horaInicio(),
                request.horaFin()

        )){
            throw new IllegalArgumentException(
                    String.format("Conflicto: el aula %s: %s ya tiene un horario registrado: %s %s - %s",
                            grupo.getAula().getId(),
                            grupo.getAula().getNombre(),
                            request.dia(),
                            request.horaInicio(),
                            request.horaFin()));
        };

    }
}
