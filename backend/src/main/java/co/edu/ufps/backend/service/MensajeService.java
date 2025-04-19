package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Mensaje;
import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.repository.MensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MensajeService {
    @Autowired
    private final MensajeRepository mensajeRepository;
    private final NotificacionService notificacionService;

    public List<Mensaje> getAllMensajes() {
        return mensajeRepository.findAll();
    }

    public Optional<Mensaje> getMensajeById(Long id) {
        return mensajeRepository.findById(id);
    }

    public Mensaje createMensaje(Mensaje mensaje) {

        // Primero se guarda el mensaje
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);

        // Luego se envía la notificación con el mensaje ya persistido
        notificacionService.enviarNotificacion(mensajeGuardado);

        return mensajeGuardado;
    }

    public Mensaje updateMensaje(Long id, Mensaje mensajeDetails) {
        return mensajeRepository.findById(id).map(mensaje -> {
            mensaje.setRemitente(mensajeDetails.getRemitente());
            mensaje.setDestinatario(mensajeDetails.getDestinatario());
            mensaje.setContenido(mensajeDetails.getContenido());
            mensaje.setFechaEnvio(mensajeDetails.getFechaEnvio());
            mensaje.setLeido(mensajeDetails.getLeido());
            return mensajeRepository.save(mensaje);
        }).orElseThrow(() -> new RuntimeException("Mensaje not found"));
    }

    public void deleteMensaje(Long id) {
        mensajeRepository.deleteById(id);
    }



    // Métodos -----------------------------------------------------------------------------------------------
    public Boolean marcarComoLeido(Long mensajeId) {
        Optional<Mensaje> optionalMensaje = mensajeRepository.findById(mensajeId);
        if (optionalMensaje.isPresent()) {
            Mensaje mensaje = optionalMensaje.get();
            if (!Boolean.TRUE.equals(mensaje.getLeido())) {
                mensaje.setLeido(Boolean.TRUE);
                mensajeRepository.save(mensaje);
                return Boolean.TRUE; // Se marcó como leído
            }
            return Boolean.FALSE; // Ya estaba leído
        } else {
            throw new RuntimeException("Mensaje no encontrado");
        }
    }


    public Mensaje responder(Long mensajeId, String contenidoRespuesta) {
        Optional<Mensaje> optionalMensaje = mensajeRepository.findById(mensajeId);
        if (optionalMensaje.isPresent()) {
            Mensaje mensajeOriginal = optionalMensaje.get();

            Mensaje respuesta = new Mensaje();
            respuesta.setRemitente(mensajeOriginal.getDestinatario()); // El destinatario original responde
            respuesta.setDestinatario(mensajeOriginal.getRemitente()); // Al remitente original
            respuesta.setContenido(contenidoRespuesta);
            respuesta.setFechaEnvio(new Date());
            respuesta.setLeido(Boolean.FALSE);

            return mensajeRepository.save(respuesta);
        } else {
            throw new RuntimeException("Mensaje original no encontrado");
        }
    }

    public List<Mensaje> reenviar(Long mensajeId, List<Persona> nuevosDestinatarios) {
        Optional<Mensaje> optionalMensaje = mensajeRepository.findById(mensajeId);
        if (optionalMensaje.isPresent()) {
            Mensaje mensajeOriginal = optionalMensaje.get();
            List<Mensaje> mensajesReenviados = new java.util.ArrayList<>();

            for (Persona destinatario : nuevosDestinatarios) {
                Mensaje nuevoMensaje = new Mensaje();
                nuevoMensaje.setRemitente(mensajeOriginal.getRemitente()); // Mismo remitente
                nuevoMensaje.setDestinatario(destinatario); // Nuevo destinatario
                nuevoMensaje.setContenido(mensajeOriginal.getContenido());
                nuevoMensaje.setFechaEnvio(new Date());
                nuevoMensaje.setLeido(Boolean.FALSE);

                Mensaje mensajeGuardado = mensajeRepository.save(nuevoMensaje);
                mensajesReenviados.add(mensajeGuardado);
            }

            return mensajesReenviados;
        } else {
            throw new RuntimeException("Mensaje original no encontrado");
        }
    }

    public List<Mensaje> obtenerMensajesNoLeidosPorDestinatario(Long destinatarioId) {
        return mensajeRepository.findByDestinatarioIdAndLeidoIsFalse(destinatarioId);
    }

}