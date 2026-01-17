package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.inscripciones.InscripcionRequest;
import com.osiel.escuela.dtos.inscripciones.InscripcionResponse;
import com.osiel.escuela.services.inscripcion.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService> {
    public InscripcionController(InscripcionService service) {
        super(service);
    }
}
