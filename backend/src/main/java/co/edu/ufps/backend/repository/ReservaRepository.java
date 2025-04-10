package co.edu.ufps.backend.repository;

import co.edu.ufps.backend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByRecursoIdAndDiaAndHoraInicioLessThanAndHoraFinGreaterThan(
            Long recursoId, Date dia, Date horaFin, Date horaInicio);
            List<Reserva> findByUsuarioCedula(Long cedula);
            List<Reserva> findByRecursoId(Long recursoId);
            List<Reserva> findByUsuarioCedulaAndDiaAfter(Long cedula, Date fechaActual);
            List<Reserva> findByDia(Date dia);
}
