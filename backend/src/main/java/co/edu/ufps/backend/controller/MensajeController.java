package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Mensaje;
import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.service.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    @Autowired
    private final MensajeService mensajeService;

    // Obtener todos los mensajes
    @GetMapping
    public List<Mensaje> getAllMensajes() {
        return mensajeService.getAllMensajes();
    }

    // Obtener un mensaje por ID
    @GetMapping("/{id}")
    public Mensaje getMensajeById(@PathVariable Long id) {
        return mensajeService.getMensajeById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
    }

    // Crear un nuevo mensaje
    @PostMapping
    public Mensaje createMensaje(@RequestBody Mensaje mensaje) {
        return mensajeService.createMensaje(mensaje);
    }

    // Actualizar un mensaje
    @PutMapping("/{id}")
    public Mensaje updateMensaje(@PathVariable Long id, @RequestBody Mensaje mensajeDetails) {
        return mensajeService.updateMensaje(id, mensajeDetails);
    }

    // Eliminar un mensaje
    @DeleteMapping("/{id}")
    public void deleteMensaje(@PathVariable Long id) {
        mensajeService.deleteMensaje(id);
    }

    // Marcar un mensaje como leído
    @PutMapping("/{id}/leido")
    public Boolean marcarComoLeido(@PathVariable Long id) {
        return mensajeService.marcarComoLeido(id);
    }

    // Responder a un mensaje
    @PostMapping("/{id}/responder")
    public Mensaje responder(@PathVariable Long id, @RequestBody String contenidoRespuesta) {
        return mensajeService.responder(id, contenidoRespuesta);
    }

    // Reenviar un mensaje a varios destinatarios
    @PostMapping("/{id}/reenviar")
    public List<Mensaje> reenviar(@PathVariable Long id, @RequestBody List<Persona> nuevosDestinatarios) {
        return mensajeService.reenviar(id, nuevosDestinatarios);
    }
}
