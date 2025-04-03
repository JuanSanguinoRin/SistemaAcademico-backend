package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {}
