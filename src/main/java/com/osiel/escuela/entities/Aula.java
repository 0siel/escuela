package com.osiel.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "AULAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_AULA")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    @NotBlank(message = "El nombre es requerido")
    @Size( min=5, max = 30, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Column(nullable = false)
    @NotNull(message = "La capacidad ds requerida")
    @Min(value = 10, message = "La capacidad mínima es 10")
    @Max(value = 50, message = "La capacidad máxima es 50")
    private Integer capacidad;

    @OneToMany(mappedBy = "aula")
    private List<Grupo> grupos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Aula aula = (Aula) o;
        return Objects.equals(id, aula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
