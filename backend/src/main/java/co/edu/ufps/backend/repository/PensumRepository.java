package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Pensum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PensumRepository extends JpaRepository<Pensum, Long> {
}
