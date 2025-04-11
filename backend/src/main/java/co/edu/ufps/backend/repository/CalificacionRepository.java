package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
     List<Calificacion> findByEstudianteCursoId(Long estudianteCursoId);
}