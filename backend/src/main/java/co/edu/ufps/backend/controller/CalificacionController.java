package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Calificacion;
import co.edu.ufps.backend.model.Curso;
import co.edu.ufps.backend.service.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/calificaciones")
@RequiredArgsConstructor

public class CalificacionController {

    private final CalificacionService calificacionService;

    // Obtener todas las calificaciones
    @GetMapping
    public List<Calificacion> getAllCalificaciones() {
        return calificacionService.getAllCalificaciones();
    }

    // Obtener calificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion> getCalificacionById(@PathVariable Long id) {
        Optional<Calificacion> calificacion = calificacionService.getCalificacionById(id);
        return calificacion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Calificacion createCalificacion(@RequestBody Calificacion calificacion) {
        return calificacionService.registrarCalificacion(calificacion);
    }

    // Eliminar calificación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalificacion(@PathVariable Long id) {
        calificacionService.deleteCalificacion(id);
        return ResponseEntity.noContent().build();
    }

    // (Opcional) Actualizar calificación básica (por ID)
    @PutMapping("/{id}")
    public ResponseEntity<Calificacion> updateCalificacion(
            @PathVariable Long id,
            @RequestBody Calificacion calificacionDetails
    ) {
        try {
            Calificacion updated = calificacionService.updateCalificacion(id, calificacionDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }





}
