package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Asignacion;
import co.edu.ufps.backend.model.Asistencia;
import co.edu.ufps.backend.service.AsignacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaciones")
@RequiredArgsConstructor
public class AsignacionController {

    private final AsignacionService asignacionService;

    /**
     * Obtener todas las asignaciones
     */
    @GetMapping
    public ResponseEntity<List<Asignacion>> getAllAsignaciones() {
        return ResponseEntity.ok(asignacionService.getAllAsignaciones());
    }

    /**
     * Obtener una asignación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAsignacionById(@PathVariable Long id) {
        return asignacionService.getAsignacionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear una nueva asignación
     */
    @PostMapping
    public ResponseEntity<Asignacion> createAsignacion(@RequestBody Asignacion asignacion) {
        return ResponseEntity.ok(asignacionService.createAsignacion(asignacion));
    }

    /**
     * Actualizar una asignación existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsignacion(@PathVariable Long id, @RequestBody Asignacion asignacion) {
        try {
            Asignacion updated = asignacionService.updateAsignacion(id, asignacion);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Eliminar una asignación por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsignacion(@PathVariable Long id) {
        asignacionService.deleteAsignacion(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/asignar-docente")
    public Asignacion asignarDocente(@RequestBody Asignacion asignacion) {
        return asignacionService.asignarDocente(
                asignacion.getDocente().getId(),
                asignacion.getCurso().getId()
        );
    }

    /**
     * Registrar asistencia a un estudiante en curso
     */
    @PostMapping("/registrar-asistencia")
    public ResponseEntity<Asistencia> registrarAsistencia(
            @RequestParam Long estudianteCursoId,
            @RequestBody Asistencia asistencia
    ) {
        return ResponseEntity.ok(asignacionService.registrarAsistencia(estudianteCursoId, asistencia));
    }
}
