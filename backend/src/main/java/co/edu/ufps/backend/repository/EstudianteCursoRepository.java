package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.EstudianteCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, Long> {

    List<EstudianteCurso> findByEstudianteCodigoEstudiante(Long codigoEstudiante);
    List<EstudianteCurso> findByEstudianteId(Long estudianteId);
    List<EstudianteCurso> findByCursoId(Long cursoId);
    Optional<EstudianteCurso> findByEstudianteCodigoEstudianteAndCursoId(Long codigoEstudiante, Long cursoId);
    List<EstudianteCurso> findByEstado(String estado);
    Optional<EstudianteCurso> findByCursoIdAndEstudianteCodigoEstudiante(Long cursoId, Long estudianteId);
    List<EstudianteCurso> findByEstudianteCodigoEstudianteAndEstado(Long estudianteId, String estado);
    List<EstudianteCurso> findByEstudianteIdAndEstado(Long estudianteId, String estado);
    Optional<EstudianteCurso> findByCursoIdAndEstudianteId(Long cursoId, Long estudianteId);

    // Método existente
    long countByCurso_IdAndEstadoIgnoreCase(Long cursoId, String estado);

    // Nuevos métodos necesarios
    @Query("SELECT ec.curso.asignatura.id FROM EstudianteCurso ec " +
            "WHERE ec.estudiante.id = :estudianteId AND LOWER(ec.estado) = LOWER(:estado)")
    List<Long> findAsignaturaIdsByEstudianteIdAndEstado(@Param("estudianteId") Long estudianteId,
                                                        @Param("estado") String estado);

    // Metodo alternativo para asignaturas aprobadas
    @Query("SELECT ec.curso.asignatura.id FROM EstudianteCurso ec " +
            "WHERE ec.estudiante.id = :estudianteId AND LOWER(ec.estado) = 'aprobado'")
    List<Long> findApprovedAsignaturaIdsByEstudianteId(@Param("estudianteId") Long estudianteId);

    // Metodo alternativo para asignaturas cursando
    @Query("SELECT ec.curso.asignatura.id FROM EstudianteCurso ec " +
            "WHERE ec.estudiante.id = :estudianteId AND LOWER(ec.estado) = 'cursando'")
    List<Long> findCurrentlyEnrolledAsignaturaIdsByEstudianteId(@Param("estudianteId") Long estudianteId);

    @Query("SELECT ec FROM EstudianteCurso ec " +
            "WHERE ec.estudiante.id = :estudianteId AND ec.curso.id = :cursoId")
    List<EstudianteCurso> findByEstudianteIdAndCursoId(@Param("estudianteId") Long estudianteId,
                                                       @Param("cursoId") Long cursoId);

}