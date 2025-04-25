package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Rol;
import co.edu.ufps.backend.model.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(RolEnum nombre);
}
