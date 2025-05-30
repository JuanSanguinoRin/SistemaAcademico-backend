package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Pensum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PensumRepository extends JpaRepository<Pensum, Long> {
    Optional<Pensum> findByPrograma_Id(Long programaId);
}
