package com.osiel.escuela.services.alumnos;

import com.osiel.escuela.dtos.alumnos.AlumnoRequest;
import com.osiel.escuela.dtos.alumnos.AlumnoResponse;
import com.osiel.escuela.entities.Alumno;
import com.osiel.escuela.mappers.AlumnoMapper;
import com.osiel.escuela.mappers.MaestroMapper;
import com.osiel.escuela.repositories.AlumnoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;
    private final MaestroMapper maestroMapper;


    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        return alumnoRepository.findAll().stream().map(alumnoMapper::entityToResponse).toList();
    }

    @Override
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entityToResponse(getAlumnoOrThrow(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        // Limpiar los datos
        String nombre = limpiar(request.nombre());
        String apellidoPaterno = limpiar(request.apellidoPaterno());
        String apellidoMaterno = limpiar(request.apellidoMaterno());

        // Generar matrícula automáticamente
        String matricula = generarMatricula();

        // Generar correo automáticamente
        String correo = generarCorreo(nombre, apellidoPaterno, apellidoMaterno, matricula);

        // Crear el alumno con los datos generados
        Alumno alumnoToSave = alumnoMapper.requestToEntity(request, matricula, correo);

        return alumnoMapper.entityToResponse(alumnoRepository.save(alumnoToSave));

    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumnoToUpdate = getAlumnoOrThrow(id);

        //Limpiar los datos(espacios en blanco)
        String nuevoNombre = limpiar(request.nombre());
        String nuevoApellidoPaterno = limpiar(request.apellidoPaterno());
        String nuevoApellidoMaterno = limpiar(request.apellidoMaterno());

        //comparar nombre, appellidoPaterno, apellido materno, matricula nuevos vs antiguos(si alguno cambió generar nuevo correo)
        boolean cambioDatosCorreo =
                !nuevoNombre.equals(alumnoToUpdate.getNombre()) ||
                        !nuevoApellidoPaterno.equals(alumnoToUpdate.getApellidoPaterno()) ||
                        !nuevoApellidoMaterno.equals(alumnoToUpdate.getApellidoMaterno());

        if(cambioDatosCorreo){

            String nuevoCorreo = generarCorreo(
                    nuevoNombre,
                    nuevoApellidoPaterno,
                    nuevoApellidoMaterno,
                    alumnoToUpdate.getMatricula()
            );

            if(alumnoRepository.existsByEmailAndIdNot(nuevoCorreo, id)){
                throw new IllegalArgumentException("El corrreo generado ya existe para otro usuario.");
            }

            alumnoToUpdate.setEmail(nuevoCorreo);
            log.info("Correo actualizado para el alumno con ID: " + id);

            Alumno actualizado = alumnoRepository.save(alumnoMapper.requestToEntity(request, alumnoToUpdate.getMatricula(), nuevoCorreo));
            log.info("Alumno actualizado: {}", actualizado);

            return alumnoMapper.entityToResponse(actualizado);

        }
        else{
            throw new IllegalArgumentException("Los datos ingresados son los mismos");
        }
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumnoToDelete = getAlumnoOrThrow(id);
        alumnoRepository.delete(alumnoToDelete);
    }

    private Alumno getAlumnoOrThrow(Long id){
        return alumnoRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No existe el alumno con el ID:" + id));
    }

    private void emailExists(String email){
        if (alumnoRepository.existsByEmailIgnoreCase(email)){
            throw new IllegalArgumentException("Ya existe un alumno asigando a ese correo");
        }
    }

    private void matriculaExist(String matricula){
        if(alumnoRepository.existsByMatriculaIgnoreCase(matricula)){
            throw new IllegalArgumentException("Ya existe un alumno con esta matrícula: " +  matricula);
        }
    }

    private String generarCorreo(
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String matricula
    ){
        nombre = normalizar(nombre).replace(" ", "");
        apellidoPaterno = normalizar(apellidoPaterno);
        apellidoMaterno = normalizar(apellidoMaterno);

        String ultimosDos = matricula.substring(8);

        return String.format("%s.%s.%s%s@alumnos.escuela.com",
                nombre.charAt(0),
                apellidoPaterno,
                apellidoMaterno.charAt(0),
                ultimosDos);
    }

    private String limpiar(String texto){
        return texto == null? "" : texto.trim();
    }

    private String normalizar(String texto){
        if(texto == null) return "";

        String sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return sinAcentos
                .toLowerCase()
                .replaceAll("[^a-z]", "");
    }

    private String generarMatricula(){
        // Generar matrícula única: año + número secuencial
        int año = LocalDate.now().getYear();
        String matriculaBase;
        String matriculaGenerada;
        int contador = 1;

        do {
            // Formato: AAAA + 6 dígitos (ej: 2026000001)
            matriculaGenerada = String.format("%d%06d", año, contador);
            contador++;
        } while (alumnoRepository.existsByMatriculaIgnoreCase(matriculaGenerada));

        return matriculaGenerada;
    }
}
