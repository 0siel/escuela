package com.osiel.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="CALIFICACIONES")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_INSCRIPCION", nullable = false, unique=true)
    @NotNull(message = "El id de la inscripción es requerido")
    private Inscripcion inscripcion;

    @Column(name="CALIFICACION", nullable = false)
    @DecimalMin(value = "0.0", message = "El valor mínimo de la calificación es 0.0")
    @DecimalMax(value = "10.0", message = "El valor miáximo de la calificación es 10.0")
    private BigDecimal calificacion;

    @Column(name="FECHA_REGISTRO")
    @NotNull(message = "La fecha de ingreso es requerida")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaRegistro = LocalDate.now();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Calificacion that = (Calificacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
