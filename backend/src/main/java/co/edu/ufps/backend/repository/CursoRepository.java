package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Asignatura;
import co.edu.ufps.backend.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByProgramaId(Long programaId);
    List<Curso> findBySemestreId(Long semestreId);

    List<Curso> findByVacacional(Boolean vacacional);

}