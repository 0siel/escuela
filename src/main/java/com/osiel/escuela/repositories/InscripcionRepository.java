package com.osiel.escuela.repositories;

import com.osiel.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByAlumnoIdAndGrupoId(Long alumno, Long grupo);

    boolean existsByAlumnoIdAndGrupoIdAndIdNot(Long alumno, Long grupo, Long id);
}
