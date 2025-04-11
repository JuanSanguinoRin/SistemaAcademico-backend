package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.HorarioCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HorarioCursoRepository extends JpaRepository<HorarioCurso, Long> {
    // Buscar por curso
    List<HorarioCurso> findAllByCursoId(Long cursoId);

    List<HorarioCurso> findByCursoId(Long cursoId);

    // Buscar por aula
    List<HorarioCurso> findByAulaId(Long aulaId);

    // Buscar por día
    List<HorarioCurso> findByDia(co.edu.ufps.backend.model.DiaSemana dia);

    // Buscar por docente (a través del curso)
    List<HorarioCurso> findByCursoDocenteId(Long docenteId);

    // Buscar por estudiante (a través del curso → EstudianteCurso)
    List<HorarioCurso> findByCursoEstudiantesEstudianteCodigoEstudiante(Long estudianteId);
}
