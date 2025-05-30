package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    List<Asignatura> findByPensum_Id(Long pensumId);

}
