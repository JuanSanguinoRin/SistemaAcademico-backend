package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

}