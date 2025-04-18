package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.HistorialAcademico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademico, Long> {
    Optional<HistorialAcademico> findByEstudianteCodigoEstudiante(Long codigoEstudiante);
}

