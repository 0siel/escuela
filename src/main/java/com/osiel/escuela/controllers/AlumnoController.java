package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.alumnos.AlumnoRequest;
import com.osiel.escuela.dtos.alumnos.AlumnoResponse;
import com.osiel.escuela.dtos.maestros.MaestroRequest;
import com.osiel.escuela.dtos.maestros.MaestroResponse;
import com.osiel.escuela.services.alumnos.AlumnoService;
import com.osiel.escuela.services.maestros.MaestroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService> {
    public AlumnoController(AlumnoService service){
        super(service);
    }
}