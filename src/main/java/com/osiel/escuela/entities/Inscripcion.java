package com.osiel.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="INSCRIPCIONES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID_ALUMNO", "ID_GRUPO"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    @NotNull(message = "El alumno es requerido")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO",nullable = false)
    @NotNull(message = "El grupo es requerido")
    private Grupo grupo;

    @Column(name="FECHA_INSCRIPCION")
    @NotNull(message = "Las fecha de inscripción es requrida")
    @PastOrPresent(message = "La fecha de inscripción no puede ser futura")
    private LocalDate fechaInscripcion = LocalDate.now();

    @OneToOne(mappedBy = "inscripcion")
    private Calificacion calificacion;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inscripcion that = (Inscripcion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
