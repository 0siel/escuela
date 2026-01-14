package com.osiel.escuela.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Getter
public enum DiaSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado");

    private final String descripcion;

    private static String quitarAcentos(String s){
        return s.toLowerCase().replace("á", "a").replace("é", "e")
                .replace("í", "i").replace("ó", "o").replace("ü", "u");

    }

    private static DiaSemana fromDescription(String descripcion){
        String buscado = quitarAcentos(descripcion.trim());
        for (DiaSemana dia: values()){
            String descDia = quitarAcentos(dia.descripcion);
            if(descDia.equals(buscado)) {
                return dia;
            }
        }

        throw new NoSuchElementException("No se encontró el dá de la semana:" + descripcion);
    }
}
