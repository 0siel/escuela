package com.osiel.escuela.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name= "MAESTROS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Maestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAESTRO")
    private Long id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El nombre es requrido")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @Column(name = "APELLIDO_PATERNO",nullable = false, length = 50)
    @NotBlank(message = "El apellido paterno es requrido")
    @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
    private String apellidoPatterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    @NotBlank(message = "El apelldio materno es requrido")
    @Size(min = 3, max = 50, message = "El apellido materno debe tener entre 3 y 50 caracteres")
    private String apellidoMaterno;

    @OneToMany(mappedBy = "maestro")
    private List<Grupo> grupos = new ArrayList<>();

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El email es requrido")
    @Size(max = 50, message = "El email debe tener máximo 100 caracteres")
    @Email(message = "El email debe tener un formato válido: (ejemplo@ejemplo.com)")
    private String email;

    @Column(nullable = false, unique = true, length = 10)
    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 10, max=10, message = "El teléfono debe tener exactamente 10 caracteres")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener 10 dígitos numéricos")
    private String telefono;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Maestro maestro = (Maestro) o;
        return Objects.equals(id, maestro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
