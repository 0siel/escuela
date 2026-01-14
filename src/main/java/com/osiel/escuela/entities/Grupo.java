package com.osiel.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "GRUPOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_CURSO", nullable = false)
    @NotNull(message = "El  curso es requerido")
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_MAESTRO", nullable = false)
    @NotNull(message = "El maestro es requerido")
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_AULA", nullable = false)
    @NotNull(message = "El Aula es requerida")
    private Aula aula;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El periodo es requerido")
    @Size(min = 6, max = 20, message = "El periodo debe tener entre 6 y 20 caracteres")
    private String periodo;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return Objects.equals(id, grupo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
