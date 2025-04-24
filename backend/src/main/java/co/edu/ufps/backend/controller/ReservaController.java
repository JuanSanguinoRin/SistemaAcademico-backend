package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Reserva;
import co.edu.ufps.backend.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    /**
     * Obtener todas las reservas
     */
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        return ResponseEntity.ok(reservaService.getAllReservas());
    }

    /**
     * Obtener una reserva por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        return reservaService.getReservaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Crear una nueva reserva
    @PostMapping("/crear")
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        // Llamamos al servicio para crear la reserva
        Reserva nuevaReserva = reservaService.crearReserva(
                reserva.getUsuario(),
                reserva.getRecurso(),
                reserva.getDia(),
                reserva.getHoraInicio(),
                reserva.getHoraFin(),
                reserva.getEsMantenimiento()
        );

        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    /**
     * Actualizar una reserva existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(
            @PathVariable Long id,
            @RequestBody Reserva reservaDetails) {
        return ResponseEntity.ok(reservaService.updateReserva(id, reservaDetails));
    }

    /**
     * Cancelar (eliminar) una reserva
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtener reservas por persona
     */
    @GetMapping("/persona/{cedula}")
    public ResponseEntity<List<Reserva>> getReservasByPersona(@PathVariable Long cedula) {
        return ResponseEntity.ok(reservaService.getReservasByPersona(cedula));
    }

    /**
     * Obtener reservas futuras por persona
     */
    @GetMapping("/persona/{cedula}/futuras")
    public ResponseEntity<List<Reserva>> getReservasFuturasByPersona(@PathVariable Long cedula) {
        return ResponseEntity.ok(reservaService.getReservasFuturasByPersona(cedula));
    }

    /**
     * Obtener reservas por recurso
     */
    @GetMapping("/recurso/{recursoId}")
    public ResponseEntity<List<Reserva>> getReservasByRecurso(@PathVariable Long recursoId) {
        return ResponseEntity.ok(reservaService.getReservasByRecurso(recursoId));
    }

    /**
     * Buscar reservas por fecha
     */
    @GetMapping("/fecha")
    public ResponseEntity<List<Reserva>> buscarReservasPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dia) {
        return ResponseEntity.ok(reservaService.buscarReservasPorFecha(dia));
    }

    // ✅ Marcar una reserva como devuelta
    @PutMapping("/devolver/{reservaId}")
    public ResponseEntity<Reserva> devolverMaterial(@PathVariable Long reservaId) {
        try {
            Reserva reserva = reservaService.marcarComoDevuelto(reservaId);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
