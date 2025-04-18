package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.Asistencia;
import co.edu.ufps.backend.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @GetMapping
    public ResponseEntity<List<Asistencia>> getAllAsistencias() {
        return ResponseEntity.ok(asistenciaService.getAllAsistencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> getAsistenciaById(@PathVariable Long id) {
        return asistenciaService.getAsistenciaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Asistencia> createAsistencia(@RequestBody Asistencia asistencia) {
        return ResponseEntity.ok(asistenciaService.createAsistencia(asistencia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> updateAsistencia(@PathVariable Long id, @RequestBody Asistencia asistenciaDetails) {
        try {
            Asistencia actualizada = asistenciaService.updateAsistencia(id, asistenciaDetails);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsistencia(@PathVariable Long id) {
        asistenciaService.deleteAsistencia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Asistencia>> getAsistenciasByFecha(@RequestParam Date fecha) {
        return ResponseEntity.ok(asistenciaService.getAsistenciasByFecha(fecha));
    }

    // Registrar asistencia desde estudianteCurso
    @PostMapping("/registrar/{estudianteCursoId}")
    public ResponseEntity<?> registrarAsistenciaDesdeEstudianteCurso(
            @PathVariable Long estudianteCursoId,
            @RequestBody Asistencia asistenciaInput
    ) {
        try {
            Asistencia creada = asistenciaService.registrarAsistencia(estudianteCursoId, asistenciaInput);
            return ResponseEntity.ok(creada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
