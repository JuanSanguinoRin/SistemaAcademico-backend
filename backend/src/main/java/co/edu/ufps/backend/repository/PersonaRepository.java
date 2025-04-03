package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
