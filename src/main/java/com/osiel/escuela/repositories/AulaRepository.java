package com.osiel.escuela.repositories;

import com.osiel.escuela.entities.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
