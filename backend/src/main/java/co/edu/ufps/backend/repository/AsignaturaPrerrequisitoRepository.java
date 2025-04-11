package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.AsignaturaPrerrequisito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsignaturaPrerrequisitoRepository extends JpaRepository<AsignaturaPrerrequisito, Long> {

    Optional<AsignaturaPrerrequisito> findByAsignatura_CodigoAndPrerrequisito_Codigo(Long codigoAsignatura, Long codigoPrerrequisito);
    List<AsignaturaPrerrequisito> findAllByAsignatura_Id(Long asignaturaId);

}
