package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.maestros.MaestroRequest;
import com.osiel.escuela.dtos.maestros.MaestroResponse;
import com.osiel.escuela.entities.Maestro;
import com.osiel.escuela.services.maestros.MaestroService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maestros")
public class MaestroController extends CommonController<MaestroRequest, MaestroResponse, MaestroService> {
    public MaestroController(MaestroService service){
        super(service);
    }
}
