package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByProgramaId(Long programaId);
    Optional<Estudiante> findByPersonaId(Long personaId);
}