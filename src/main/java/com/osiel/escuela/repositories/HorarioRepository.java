package com.osiel.escuela.repositories;

import com.osiel.escuela.entities.Horario;
import com.osiel.escuela.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    @Query("""
        SELECT COUNT(h) > 0 FROM Horario h 
        WHERE h.dia = :dia 
        AND h.grupo.periodo = :periodo 
        AND (h.grupo.maestro.id = :maestroId)
        AND (:inicio < h.horaFin AND h.horaInicio < :fin)
    """)
    boolean existeConflictoMaestro(
            @Param("maestroId") Long maestroId,
            @Param("periodo") String periodo,
            @Param("dia") DiaSemana dia,
            @Param("inicio") LocalTime inicio,
            @Param("fin") LocalTime fin
    );

    @Query("""
        SELECT COUNT(h) > 0 FROM Horario h 
        WHERE h.dia = :dia 
        AND h.grupo.periodo = :periodo 
        AND (h.grupo.aula.id = :aulaId)
        AND (:inicio < h.horaFin AND h.horaInicio < :fin)
    """)
    boolean existeConflictoAula(
            @Param("aulaId") Long aulaId,
            @Param("periodo") String periodo,
            @Param("dia") DiaSemana dia,
            @Param("inicio") LocalTime inicio,
            @Param("fin") LocalTime fin
    );

    boolean existsByGrupoId(Long id);
}
