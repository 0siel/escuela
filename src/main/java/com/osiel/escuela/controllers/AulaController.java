package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.aulas.AulaRequest;
import com.osiel.escuela.dtos.aulas.AulaResponse;
import com.osiel.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService>{
    public AulaController(AulaService service){
        super(service);
    }
}
