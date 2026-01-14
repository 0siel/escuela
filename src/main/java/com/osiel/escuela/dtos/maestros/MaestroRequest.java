package com.osiel.escuela.dtos.maestros;

import com.osiel.escuela.entities.Grupo;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public record MaestroRequest(

        @NotBlank(message = "El nombre es requrido")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es requrido")
        @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
        String apellidoPatterno,
        @NotBlank(message = "El apelldio materno es requrido")
        @Size(min = 3, max = 50, message = "El apellido materno debe tener entre 3 y 50 caracteres")
        String apellidoMaterno,

        @OneToMany(mappedBy = "maestro")
        List<Grupo> grupos,

        @NotBlank(message = "El email es requrido")
        @Size(max = 50, message = "El email debe tener máximo 100 caracteres")
        @Email(message = "El email debe tener un formato válido: (ejemplo@ejemplo.com)")
        String email,

        @NotBlank(message = "El teléfono es requerido")
        @Size(min = 10, max=10, message = "El teléfono debe tener exactamente 10 caracteres")
        @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener 10 dígitos numéricos")
        String telefono
) {

}
