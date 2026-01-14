package com.osiel.escuela.repositories;

import com.osiel.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
}
