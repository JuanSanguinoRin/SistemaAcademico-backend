package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Mensaje;
import co.edu.ufps.backend.model.Notificacion;
import co.edu.ufps.backend.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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



    // Métodos específicos -----------------------------------------------------------------------------------------------
    public Notificacion enviarNotificacion(Mensaje mensaje) {
        if (mensaje == null || mensaje.getDestinatario() == null) {
            throw new RuntimeException("Mensaje o destinatario no válido para notificación");
        }

        Notificacion notificacion = new Notificacion();
        String contenido = mensaje.getContenido();
        String preView = contenido.length() > 30 ? contenido.substring(0, 30) + "..." : contenido;

        notificacion.setPreView(preView);
        notificacion.setFechaEnvio(new Date());
        notificacion.setMensaje(mensaje);
        notificacion.setDestinatario(mensaje.getDestinatario());

        return notificacionRepository.save(notificacion);
    }


    public boolean marcarComoAbierto(Long notificacionId) {
        Optional<Notificacion> optionalNotificacion = notificacionRepository.findById(notificacionId);
        if (optionalNotificacion.isPresent()) {
            Notificacion notificacion = optionalNotificacion.get();
            if (notificacion.getAbierta() == null || !notificacion.getAbierta()) {
                notificacion.setAbierta(Boolean.TRUE); // Marcamos como abierta
                notificacionRepository.save(notificacion);
                return true; // Se marcó como abierta
            }
            return false; // Ya estaba abierta
        } else {
            throw new RuntimeException("Notificación no encontrada");
        }
    }

    public List<Notificacion> getNotificacionesPorPersona(Long personaId) {
        return notificacionRepository.findByDestinatarioId(personaId);
    }

}