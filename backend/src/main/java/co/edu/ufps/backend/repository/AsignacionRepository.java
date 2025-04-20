package co.edu.ufps.backend.repository;
import co.edu.ufps.backend.model.Asignacion;
import co.edu.ufps.backend.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    Optional<Asignacion> findByCursoId(Long cursoId);
    List<Curso> findByDocenteId(Long docenteId);
    Optional<Asignacion> findByDocenteIdAndCursoId(Long docenteId, Long cursoId);
}