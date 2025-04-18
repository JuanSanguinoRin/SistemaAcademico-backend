package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Notificacion;
import co.edu.ufps.backend.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionService {
    @Autowired
    private final NotificacionRepository notificacionRepository;

    public List<Notificacion> getAllNotificaciones() {
        return notificacionRepository.findAll();
    }

    public Optional<Notificacion> getNotificacionById(Long id) {
        return notificacionRepository.findById(id);
    }

    public Notificacion createNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public Notificacion updateNotificacion(Long id, Notificacion notificacionDetails) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacion.setPreView(notificacionDetails.getPreView());
            notificacion.setFechaEnvio(notificacionDetails.getFechaEnvio());
            notificacion.setMensaje(notificacionDetails.getMensaje());
            notificacion.setDestinatario(notificacionDetails.getDestinatario());
            return notificacionRepository.save(notificacion);
        }).orElseThrow(() -> new RuntimeException("Notificacion not found"));
    }

    public void deleteNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }

    // Métodos específicos
    public void enviarNotificacion() {
        // Implementación del método
    }

    public void marcarComoAbierto() {
        // Implementación del método
    }
}