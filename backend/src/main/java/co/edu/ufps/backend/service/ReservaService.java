package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Reserva;
import co.edu.ufps.backend.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}

