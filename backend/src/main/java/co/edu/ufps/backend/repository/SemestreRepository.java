package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemestreRepository extends JpaRepository<Semestre, Long> {
}
