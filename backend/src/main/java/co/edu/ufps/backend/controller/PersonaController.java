package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Persona;
import co.edu.ufps.backend.model.Reserva;
import co.edu.ufps.backend.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    // Obtener todas las personas
    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        return ResponseEntity.ok(personaService.getAllPersonas());
    }

    // Obtener una persona por id
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaByCedula(@PathVariable Long id) {
        Optional<Persona> persona = personaService.getPersonaByCedula(id);
        return persona.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva persona
    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        try {
            Persona nuevaPersona = personaService.createPersona(persona);
            return ResponseEntity.ok(nuevaPersona);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Modificar datos de una persona
    @PutMapping("/{id}")
    public ResponseEntity<Persona> modificarDatos(@PathVariable Long id, @RequestBody Persona personaDetalles) {
        try {
            Persona personaActualizada = personaService.modificarDatos(id, personaDetalles);
            return ResponseEntity.ok(personaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar datos de una persona (eliminación lógica)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDatos(@PathVariable Long id) {
        try {
            personaService.eliminarDatos(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // eesto todavia no
    @PostMapping("/{id}/reservas")
    public ResponseEntity<?> crearReservaParaPersona(
            @PathVariable Long cedula,
            @RequestParam Long recursoId,
            @RequestParam Date dia,
            @RequestParam Date horaInicio,
            @RequestParam Date horaFin
    ) {
        try {
            Reserva reserva = personaService.crearReservaParaPersona(cedula, recursoId, dia, horaInicio, horaFin);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
