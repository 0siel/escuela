package com.osiel.escuela.repositories;

import com.osiel.escuela.entities.Alumno;
import com.osiel.escuela.entities.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMatriculaIgnoreCase(String matricula);

    boolean existsByEmailAndIdNot(String email, Long id );


}
