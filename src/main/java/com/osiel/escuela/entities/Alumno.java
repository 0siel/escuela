package com.osiel.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ALUMNOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El nombre es requrido")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    @NotBlank(message = "El apellido paterno es requrido")
    @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    @NotBlank(message = "El apellido materno es requrido")
    @Size(min = 3, max = 50, message = "El apellido materno debe tener entre 3 y 50 caracteres")
    private String apellidoMaterno;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El email es requrido")
    @Size(max = 50, message = "El email debe tener máximo 100 caracteres")
    @Email(message = "El email debe tener un formato válido: (ejemplo@ejemplo.com)")
    private String email;

    @Column(nullable = false, unique = true, length = 10)
    @NotBlank(message = "La matricula es requerida")
    @Size(min=10,max = 10, message = "La matrícula debe tener exactamente 10 caracteres")

    private String matricula;


    @Column(name="FECHA_INGRESO")
    @NotNull(message="La fecha de ingreso es requerida")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso = LocalDate.now();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(id, alumno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}