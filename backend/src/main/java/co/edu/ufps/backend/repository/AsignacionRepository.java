package co.edu.ufps.backend.repository;
import co.edu.ufps.backend.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    Optional<Asignacion> findByCursoId(Long cursoId);
}