package co.edu.ufps.backend.repository;
import co.edu.ufps.backend.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {}

