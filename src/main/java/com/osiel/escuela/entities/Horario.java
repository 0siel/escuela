package com.osiel.escuela.entities;

import com.osiel.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name="HORARIOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_HORARIO", nullable = false)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    @NotNull(message = "El diá de la semana es requrido")
    private DiaSemana dia;

    @Column(name = "HORA_INICIO", nullable = false, length = 5)
    @NotBlank(message = "La hora de inicio es requrida")
    @Pattern(regexp = "^[0-9]{2}:[0-9]{2}$", message = "El formato de la hora de inicio es HH:mm")
    private String horaInicio;

    @Column(name="HORA_FIN", nullable = false, length = 5)
    @Pattern(regexp = "^[0-9]{2}:[0-9]{2}$", message = "El formato de la hora de inicio es HH:mm")
    private String horaFin;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return Objects.equals(id, horario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
