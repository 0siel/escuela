package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.calificaciones.CalificacionRequest;
import com.osiel.escuela.dtos.calificaciones.CalificacionResponse;
import com.osiel.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}
