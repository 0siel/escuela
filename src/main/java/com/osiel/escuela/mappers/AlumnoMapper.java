package com.osiel.escuela.mappers;

import com.osiel.escuela.dtos.alumnos.AlumnoRequest;
import com.osiel.escuela.dtos.alumnos.AlumnoResponse;
import com.osiel.escuela.dtos.alumnos.DatosCalificacion;
import com.osiel.escuela.entities.Alumno;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.entities.Inscripcion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "EEEE, dd 'de' MMMM 'de' yyyy",
            new Locale("es", "MX")
    );

    @Override
    public AlumnoResponse entityToResponse(Alumno entity){
        if(entity == null) return null;

        String fecha = entity.getFechaIngreso().format(formatter);

        List<DatosCalificacion> calificaciones = alumnoToDatosCalificaciones(entity);
        BigDecimal promedio = calcularPromedioReal(entity);

        return new AlumnoResponse(
                entity.getId(),
                String.join(" ", entity.getNombre(), entity.getApellidoPaterno(), entity.getApellidoMaterno()),
                entity.getEmail(),
                entity.getMatricula(),
                fecha.substring(0,1).toUpperCase()  + fecha.substring(1),
                calificaciones,
                promedio
        );
    }

    @Override
    public Alumno requestToEntity(AlumnoRequest request) {
        if(request == null) return null;

        Alumno alumno = new Alumno();
        alumno.setNombre(request.nombre());
        alumno.setApellidoPaterno(request.apellidoPaterno());
        alumno.setApellidoMaterno(request.apellidoMaterno());

        alumno.setFechaIngreso(LocalDate.now());
        return alumno;

    }

    public Alumno requestToEntity(AlumnoRequest request, String matricula, String email) {
        if(request == null) return null;

        Alumno alumno = requestToEntity(request);
        alumno.setMatricula(matricula);
        alumno.setEmail(email);
        return alumno;

    }

    private List<DatosCalificacion> alumnoToDatosCalificaciones(Alumno alumno) {
        if (alumno == null) return null;

        return alumno.getInscripciones().stream()

                .filter(i -> i.getCalificacion() != null && i.getCalificacion().getCalificacion() != null)
                .map(inscripcion -> new DatosCalificacion(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion().getCalificacion() // Ya es seguro acceder
                )).toList();
    }

    public BigDecimal calcularPromedioReal(Alumno alumno) {
        if (alumno.getInscripciones() == null || alumno.getInscripciones().isEmpty()) {
            return BigDecimal.ZERO;
        }

        // 1. Filtramos solo las inscripciones que tienen calificación cargada
        List<BigDecimal> calificacionesValidas = alumno.getInscripciones().stream()
                .map(Inscripcion::getCalificacion) // Obtenemos el objeto Calificacion
                .filter(Objects::nonNull)           // Filtro: ¿Existe el objeto?
                .map(Calificacion::getCalificacion) // Obtenemos el BigDecimal
                .filter(Objects::nonNull)           // Filtro: ¿Tiene valor numérico?
                .toList();

        // 2. Si no hay ninguna materia calificada aún, el promedio es 0
        if (calificacionesValidas.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // 3. Sumamos los valores
        BigDecimal suma = calificacionesValidas.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Dividimos entre el tamaño de la lista FILTRADA (no la original)
        return suma.divide(
                BigDecimal.valueOf(calificacionesValidas.size()),
                2,
                RoundingMode.HALF_UP
        );
    }

}
