package com.osiel.escuela.dtos;

public record ErrorResponse(
        int codigo,
        String mensaje
) {
}
