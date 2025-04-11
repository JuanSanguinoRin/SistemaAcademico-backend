package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.HorarioCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HorarioCursoRepository extends JpaRepository<HorarioCurso, Long>
{

    Optional<HorarioCurso> findByCursoId(Long id);

}
