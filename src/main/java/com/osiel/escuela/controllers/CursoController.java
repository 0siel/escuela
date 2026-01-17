package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.cursos.CursoRequest;
import com.osiel.escuela.dtos.cursos.CursoResponse;
import com.osiel.escuela.services.cursos.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
public class CursoController extends CommonController<CursoRequest, CursoResponse, CursoService> {
    public CursoController(CursoService service){
        super(service);
    }
}
