package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.EstudianteCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, Long> {
    List<EstudianteCurso> findByEstudianteCodigoEstudiante(Long codigoEstudiante);
    List<EstudianteCurso> findByCursoId(Long cursoId);
    Optional<EstudianteCurso> findByEstudianteCodigoEstudianteAndCursoId(Long codigoEstudiante, Long cursoId);
    List<EstudianteCurso> findByEstado(String estado);
    Optional<EstudianteCurso> findByCursoIdAndEstudianteCodigoEstudiante(Long cursoId, Long estudianteId);


}