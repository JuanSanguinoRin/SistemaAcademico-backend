package co.edu.ufps.backend.repository;
import co.edu.ufps.backend.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByEstudianteCursoId(Long estudianteCursoId);
}

