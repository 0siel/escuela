package com.osiel.escuela.controllers;

import com.osiel.escuela.dtos.grupos.GrupoRequest;
import com.osiel.escuela.dtos.grupos.GrupoResponse;
import com.osiel.escuela.services.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService>{

    public GrupoController(GrupoService service) {
        super(service);
    }
}
