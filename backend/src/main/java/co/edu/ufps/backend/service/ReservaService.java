package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Reserva;
import co.edu.ufps.backend.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {
    @Autowired
    private final ReservaRepository reservaRepository;

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva createReserva(Reserva reserva) {
        // Validar si el recurso ya está reservado en el mismo horario
        List<Reserva> reservasExistentes = reservaRepository
                .findByRecursoIdAndDiaAndHoraInicioLessThanAndHoraFinGreaterThan(
                        reserva.getRecurso().getId(),
                        reserva.getDia(),
                        reserva.getHoraFin(),
                        reserva.getHoraInicio()
                );

        if (!reservasExistentes.isEmpty()) {
            throw new RuntimeException("El recurso ya está reservado en ese horario.");
        }

        return reservaRepository.save(reserva);
    }

    public Reserva updateReserva(Long id, Reserva reservaDetails) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setUsuario(reservaDetails.getUsuario());
            reserva.setDia(reservaDetails.getDia());
            reserva.setHoraInicio(reservaDetails.getHoraInicio());
            reserva.setHoraFin(reservaDetails.getHoraFin());
            reserva.setRecurso(reservaDetails.getRecurso());
            return reservaRepository.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva not found"));
    }

    // Obtener todas las reservas de una persona
    public List<Reserva> getReservasByPersona(Long cedula) {
        return reservaRepository.findByUsuarioCedula(cedula);
    }

    // Obtener todas las reservas de un recurso
    public List<Reserva> getReservasByRecurso(Long recursoId) {
        return reservaRepository.findByRecursoId(recursoId);
    }

    // Obtener reservas futuras de una persona
    public List<Reserva> getReservasFuturasByPersona(Long cedula) {
        Date hoy = new Date();
        return reservaRepository.findByUsuarioCedulaAndDiaAfter(cedula, hoy);
    }

    // Buscar reservas por fecha específica
    public List<Reserva> buscarReservasPorFecha(Date dia) {
        return reservaRepository.findByDia(dia);
    }

    // Cancelar una reserva (puede ser lógica o eliminación directa)
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reservaRepository.delete(reserva); // o marcar como cancelada si prefieres
    }

    public boolean estaRecursoOcupado(Long recursoId, Date dia, Date horaInicio, Date horaFin) {
        List<Reserva> reservas = reservaRepository.findAll();

        for (Reserva r : reservas) {
            if (r.getRecurso().getId().equals(recursoId) && r.getDia().equals(dia)) {
                boolean solapa =
                        horaInicio.before(r.getHoraFin()) && horaFin.after(r.getHoraInicio());
                if (solapa) return true;
            }
        }
        return false;
    }
}

