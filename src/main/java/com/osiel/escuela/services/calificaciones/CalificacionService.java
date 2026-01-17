package com.osiel.escuela.services.calificaciones;

import com.osiel.escuela.dtos.calificaciones.CalificacionRequest;
import com.osiel.escuela.dtos.calificaciones.CalificacionResponse;
import com.osiel.escuela.entities.Calificacion;
import com.osiel.escuela.services.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CalificacionService extends CrudService<CalificacionRequest, CalificacionResponse> {
}
