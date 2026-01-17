package com.osiel.escuela.repositories;

import com.osiel.escuela.entities.Aula;
import com.osiel.escuela.entities.Curso;
import com.osiel.escuela.entities.Grupo;
import com.osiel.escuela.entities.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    boolean existsById(Long id);
    boolean existsByCursoId(Long cursoId);
    boolean existsByAulaId(Long aulaId);
    boolean existsByCursoAndMaestroAndAulaAndPeriodo(
            Curso curso,
            Maestro maestro,
            Aula aula,
            String periodo
    );

    boolean existsByCursoAndMaestroAndAulaAndPeriodoAndIdNot(
            Curso curso, Maestro maestro, Aula aula, String periodo, Long id
    );
}
