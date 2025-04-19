package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Mensaje;
import co.edu.ufps.backend.model.Notificacion;
import co.edu.ufps.backend.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    @Autowired
    private final NotificacionService notificacionService;

    // Obtener todas las notificaciones
    @GetMapping
    public List<Notificacion> getAllNotificaciones() {
        return notificacionService.getAllNotificaciones();
    }

    // Obtener una notificación por su ID
    @GetMapping("/{id}")
    public Optional<Notificacion> getNotificacionById(@PathVariable Long id) {
        return notificacionService.getNotificacionById(id);
    }

    // Crear una nueva notificación (manual)
    @PostMapping
    public Notificacion createNotificacion(@RequestBody Notificacion notificacion) {
        return notificacionService.createNotificacion(notificacion);
    }

    // Actualizar una notificación
    @PutMapping("/{id}")
    public Notificacion updateNotificacion(@PathVariable Long id, @RequestBody Notificacion notificacionDetails) {
        return notificacionService.updateNotificacion(id, notificacionDetails);
    }

    // Eliminar una notificación
    @DeleteMapping("/{id}")
    public void deleteNotificacion(@PathVariable Long id) {
        notificacionService.deleteNotificacion(id);
    }

    // Enviar notificación a partir de un mensaje
    @PostMapping("/enviar")
    public Notificacion enviarNotificacion(@RequestBody Mensaje mensaje) {
        return notificacionService.enviarNotificacion(mensaje);
    }

    // Marcar una notificación como abierta
    @PutMapping("/abrir/{id}")
    public boolean marcarComoAbierto(@PathVariable Long id) {
        return notificacionService.marcarComoAbierto(id);
    }

//este es el malp que hay que usar en el fronetn
    @GetMapping("/persona/{personaId}")
    public List<Notificacion> getNotificacionesPorPersona(@PathVariable Long personaId) {
        return notificacionService.getNotificacionesPorPersona(personaId);
    }


}
