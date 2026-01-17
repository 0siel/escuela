package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.horarios.HorarioRequest;
import com.osiel.escuela.dtos.horarios.HorarioResponse;
import com.osiel.escuela.services.horarios.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService> {

    public HorarioController(HorarioService service) {
        super(service);
    }
}
