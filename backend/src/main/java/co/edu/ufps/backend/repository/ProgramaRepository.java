package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
}
