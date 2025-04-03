package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

}