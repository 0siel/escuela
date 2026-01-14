package com.osiel.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Objects;


@Entity
@Table(name = "CURSOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 5, max = 100, message = "El nombre debe tener entre 5 y 100 caracteres")
    private String nombre;

    @Column(length = 200)
    @Size(max = 200, message = "La descripción debe tener entre 5 y 50 caracteres")
    private String descripcion;

    @Column(nullable = false)
    @NotNull(message = "Los creditos son requeridos")
    @Min(value=1, message = "Los créditos deben ser mayores a 1")
    @Max(value=10, message = "Los créditos deben ser menores a 10")
    private Integer creditos;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return Objects.equals(id, curso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
