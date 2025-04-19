package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatarioId(Long destinatarioId);
}