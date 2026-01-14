package com.osiel.escuela.dtos.maestros;

public record MaestroResponse(Long id,
                              String nombre,
                              String email,
                              String telefono) {
}
